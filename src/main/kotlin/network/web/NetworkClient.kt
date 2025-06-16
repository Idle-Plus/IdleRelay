package dev.uraxys.idleclient.network.web

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.uraxys.idleclient.ClientRelay
import dev.uraxys.idleclient.network.PacketManager
import dev.uraxys.idleclient.network.ServerType
import dev.uraxys.idleclient.network.internal.RelayConnection
import dev.uraxys.idleclient.network.internal.WatsonPacket
import dev.uraxys.idleclient.network.types.packets.LoginDataMessage
import dev.uraxys.idleclient.utils.utils.Common
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import kotlinx.coroutines.launch

const val PING_TIME = 30_000

class NetworkClient(
	private val network: NetworkManager,

	/**
	 * The internal network id given to this player connection.
	 */
	val networkId: Int,
	/**
	 * The WebSocket session for this player connection.
	 */
	val client: DefaultWebSocketSession,

	/**
	 * The session ticket used for this player.
	 *
	 * I'm not a fan of storing the session ticket in memory, but it's
	 * necessary if the connection to the chat drops, and we want to
	 * reconnect.
	 *
	 * Maybe we could just get the client to resend their session ticket.
	 */
	val sessionTicket: String,
	/**
	 * The client version of this player.
	 */
	val clientVersion: String,
	/**
	 * The config version of this player.
	 */
	val configVersion: Double,
	/**
	 * A unique id for this player that will be sent to the game server.
	 */
	val uniqueDeviceId: String
) {

	/**
	 * The connection to the game server.
	 */
	var game: RelayConnection? = null
	/**
	 * The connection to the chat server.
	 */
	var chat: RelayConnection? = null

	/**
	 * If the player is connected to the game server.
	 */
	var connected = false; private set
	/**
	 * If the player has been authenticated by the game server.
	 */
	var authenticated = false; private set
	/**
	 * If we've closed the connection.
	 */
	var closed = false; private set

	/**
	 * The last time we sent a ping to the server.
	 */
	private var lastPing: Long = System.currentTimeMillis()

	suspend fun tick() {
		// Ping the server.
		if (this.lastPing + PING_TIME < System.currentTimeMillis()) {
			this.lastPing = System.currentTimeMillis()
			this.game?.ping()
			this.chat?.ping()
		}
	}

	suspend fun sendToInternal(destination: ServerType, packet: String) {
		if (this.closed) throw IllegalStateException("Tried to send a packet to a closed NetworkClient.")
		if (!this.connected) throw IllegalStateException("Tried to send a packet to a NetworkClient that is not connected.")

		if (destination == ServerType.GAME) {
			if (this.game == null) {
				throw IllegalStateException("Tried to send a packet to a NetworkClient that is not connected to the game server.")
			}

			// TODO: Should probably be using a queue, then kill the connection
			//       if the queue becomes too full from the server not being
			//       able to handle our packets.
			this.game!!.send(WatsonPacket.withPayload(packet))
			return
		}

		throw UnsupportedOperationException("Chat server is not supported yet.")
	}

	suspend fun sendToWeb(packet: String) {
		if (this.closed) throw IllegalStateException("Tried to send a packet to a closed NetworkClient.")
		if (!this.connected) throw IllegalStateException("Tried to send a packet to a NetworkClient that is not connected.")
		this.client.send(Frame.Text(packet))
	}

	suspend fun disconnect() {
		if (this.closed) return

		this.client.close()
		this.game?.disconnect()
		this.chat?.disconnect()

		this.authenticated = false
		this.closed = true
	}

	/*
	 * Message Handlers
	 */

	fun `$onConnectionOpen`(from: ServerType) {
		if (this.closed) return
		log.info("Connection to $from opened.")

		if (from == ServerType.GAME) {
			this.connected = true
		}
	}

	fun `$onConnectionClose`(from: ServerType) {
		if (this.closed) return
		log.info("Connection to $from closed.")

		if (from == ServerType.GAME) {
			if (this.closed) return
			ClientRelay.NETWORK_SCOPE.launch { disconnect() }
			return
		}
	}

	fun `$onAuthMessage`(from: ServerType, payload: String) {
		val packet = Common.JSON.readTree(payload)
		if (from == ServerType.GAME) {
			if (packet?.get("MsgType")?.asInt() != 1) {
				// Well, I didn't expect this, but the server might send you
				// game-related packets before the authentication response,
				// that's... fun.
				this.`$onNetworkMessage`(from, payload, true)
				return
			}

			// TODO: remove verbose logging
			log.info("Received auth response packet: ${packet.toPrettyString()}")

			// Re-hydrate the packet into a LoginDataMessage object.
			val packet = Common.RELAY_INBOUND_NETWORK_JSON.treeToValue<LoginDataMessage>(packet)

			// Authenticate the client and send the packet to the client.
			this.authenticated = true
			this.network.authenticateClient(this)
			ClientRelay.NETWORK_SCOPE.launch { sendToWeb(Common.RELAY_OUTBOUND_NETWORK_JSON.writeValueAsString(packet)) }
			return
		}

		throw UnsupportedOperationException("Chat server is not supported yet.")
	}

	fun `$onNetworkMessage`(from: ServerType, payload: String, ignoreAuth: Boolean = false) {
		var payload = payload

		if (from == ServerType.GAME) {
			if (!this.authenticated && !ignoreAuth) {
				throw IllegalStateException("Received a packet from $from before authentication.")
			}

			// TODO: remove verbose logging
			log.info("Received packet: $payload")

			// Try to re-hydrate the packet before sending it to the client.
			val json = Common.JSON.readTree(payload) as? ObjectNode
			if (json != null) {
				val packet = PacketManager.decode(json)
				if (packet != null) payload = Common.RELAY_OUTBOUND_NETWORK_JSON.writeValueAsString(packet)
				else log.warn("Failed to re-hydrate packet: $json")
			}

			ClientRelay.NETWORK_SCOPE.launch { sendToWeb(payload) }
			return
		}

		throw UnsupportedOperationException("Chat server is not supported yet.")
	}

	companion object {
		private val log = LogUtils.create(NetworkClient::class)
	}
}