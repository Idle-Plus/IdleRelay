package dev.uraxys.idleclient.network.web

import com.fasterxml.jackson.databind.node.ObjectNode
import dev.uraxys.idleclient.ClientRelay
import dev.uraxys.idleclient.ServerState
import dev.uraxys.idleclient.network.ServerType
import dev.uraxys.idleclient.utils.extensions.close
import dev.uraxys.idleclient.utils.utils.Common
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.origin
import io.ktor.websocket.CloseReason.Codes
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class NetworkManager(
	private val server: ClientRelay
) {

	fun authenticateClient(client: NetworkClient) {
		val preAuthClient = PRE_AUTH_CONNECTIONS.remove(client.networkId) ?: return
		CONNECTIONS[client.networkId] = preAuthClient
		log.info("Authenticated client ${client.networkId}.")
	}

	suspend fun tick() = coroutineScope {
		CONNECTIONS
			.map { (_, client) -> launch { client.tick() } }
			.joinAll()
	}

	suspend fun handleConnection(call: ApplicationCall, session: DefaultWebSocketSession) {
		// Don't accept new connections if we're not "open".
		if (server.state != ServerState.RUNNING) {
			session.close(Codes.GOING_AWAY)
			return
		}

		var client: NetworkClient? = null

		try {
			session.incoming.consumeEach {
				// We only accept text frames.
				if (it !is Frame.Text) {
					session.close(Codes.PROTOCOL_ERROR)
					return@consumeEach
				}

				if (!it.fin) return@consumeEach
				val content = it.readText()

				// If the client is null, then we'll only accept an "auth" packet.
				if (client == null) {
					val payload = Common.JSON.readTree(content)
					if (payload !is ObjectNode) {
						session.close(Codes.PROTOCOL_ERROR)
						return@consumeEach
					}

					// Make sure it's an auth packet.
					val type = payload.get("MsgType")?.asInt()
					if (type != 2) {
						log.warn("Client sent an invalid packet type while in auth stage: $type")
						session.close(Codes.PROTOCOL_ERROR)
						return@consumeEach
					}

					val result = handleClientAuth(payload, call, session)
					if (result == null) {
						session.close(Codes.PROTOCOL_ERROR)
						return@consumeEach
					}

					client = result
					PRE_AUTH_CONNECTIONS[client.networkId] = client
					log.info("Client ${client.networkId} connected.")
					return@consumeEach
				}

				if (!client.authenticated) {
					val payload = Common.JSON.readTree(content)
					if (payload is ObjectNode && payload.get("MsgType")?.asInt() == 2 && payload.has("ForceLogIn")) {
						// Client is trying to force login, allow it.
						client.sendToInternal(ServerType.GAME, content)
						return@consumeEach
					}

					log.warn("Client ${client.networkId} sent a packet before completing authentication.")
					session.close(Codes.VIOLATED_POLICY)
					return@consumeEach
				}

				// TODO: Handle custom packets, e.g., asking the relay server
				//       to do API calls with an higher rate limit token.

				// For now, we only support sending packets to the game server.
				client.sendToInternal(ServerType.GAME, content)
			}
		} catch (e: Exception) {
			currentCoroutineContext().ensureActive()
			log.error("Error while handling connection.", e)

			// If the client isn't authenticated, then close the session.
			if (client == null || !client.authenticated)
				session.close(Codes.INTERNAL_ERROR, "Internal server error.")
		} finally {
			// If the client isn't null, remove it from the list of clients.
			if (client != null) {
				PRE_AUTH_CONNECTIONS.remove(client.networkId)
				CONNECTIONS.remove(client.networkId)
				client.disconnect()
				log.info("Client disconnected: ${client.networkId}")
			}
		}
	}

	private suspend fun handleClientAuth(node: ObjectNode, call: ApplicationCall, session: DefaultWebSocketSession): NetworkClient? {
		val sessionTicket = node.get("SessionTicket")?.asText()
			?: return null
		val clientVersion = node.get("ClientVersion")?.asText()
			?: return null
		val configVersion = node.get("ConfigVersion")?.asDouble()
			?: return null

		// "Try" to create a unique fingerprint for this client.
		// This is used for 2FA on the game server.

		// TODO: Replace the session token with some cookie or data
		//       stored on the client.

		val clientIp = call.request.origin.remoteAddress
		val userAgent = call.request.headers["User-Agent"] ?: "Unknown"

		val md = MessageDigest.getInstance("SHA-256")
		val uniqueId = md.digest((clientIp + userAgent).toByteArray())
			.joinToString("") { "%02x".format(it) }

		val client = NetworkClient(
			this,
			NEXT_CONNECTION_ID.getAndIncrement(),
			session,
			sessionTicket,
			clientVersion,
			configVersion,
			"${uniqueId}/IdlePlus/Client/${clientIp}"
		)

		// Attempt to connect the client to the game server.
		val result = this.server.gameNetwork.connect(client)
		if (!result) {
			log.warn("Failed to connect client ${client.networkId} to game server.")
			return null
		}

		return client
	}

	companion object {
		private val log = LogUtils.create(NetworkManager::class)

		private val PRE_AUTH_CONNECTIONS: ConcurrentHashMap<Int, NetworkClient> = ConcurrentHashMap()
		private val CONNECTIONS: ConcurrentHashMap<Int, NetworkClient> = ConcurrentHashMap()

		private val NEXT_CONNECTION_ID = AtomicInteger(0)
	}
}