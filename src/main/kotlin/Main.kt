package dev.uraxys.idleclient

import dev.uraxys.idleclient.utils.Utils
import io.ktor.server.application.install
import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.sslConnector
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.KeyStore

import java.util.Properties
import kotlin.time.Duration.Companion.seconds

fun main() {
	// Load the relay config.
	val config = RelayConfig()

	// Create our relay server.
	ClientRelay.SERVER = ClientRelay(config)
	val server = ClientRelay.SERVER

	// Create our ktor web server.
	ClientRelay.WEB_SERVER = embeddedServer(
		Netty,
		configure = {

			this.connectors.addAll(arrayOf(
				EngineConnectorBuilder().apply {
					this.port = if (config.secure) config.port - 1 else config.port
					this.host = "0.0.0.0"
				}
			))

			if (config.secure) {
				sslConnector(
					keyStore = getKeyStore(config.keyStoreFile, config.keyStorePassword),
					keyAlias = config.keyStoreName,
					keyStorePassword = { config.keyStorePassword.toCharArray() },
					privateKeyPassword = { config.keyStorePassword.toCharArray() },
					builder = {
						this.port = config.port
					}
				)
			}
		},
		module = {
			install(WebSockets) {
				pingPeriod = 15.seconds
				timeout = 30.seconds
				maxFrameSize = Long.MAX_VALUE
				masking = false
			}

			routing { webSocket("/") {
				ClientRelay.SERVER.networkManager.handleConnection(call, this)
			} }
		}

	)

	// Start the server.
	Thread({
		@Suppress("OPT_IN_USAGE")
		GlobalScope.launch {
			server.start()
		}
	}, "WebChatServer").start()

	// Wait until the server has started.
	val waiting = System.currentTimeMillis()
	while (server.state != ServerState.RUNNING && server.state != ServerState.STOPPED &&
		waiting + 20_000 > System.currentTimeMillis()) Thread.sleep(100)
	if (server.state == ServerState.STOPPED) return

	// Start the web server.
	ClientRelay.WEB_SERVER.start(wait = false)

	// Wait until the server has stopped.
	while (server.state != ServerState.STOPPED)
		Thread.sleep(500)
}

fun getKeyStore(filename: String, password: String): KeyStore {
	val keyStore = KeyStore.getInstance("PKCS12")

	val file = Utils.file(Utils.userDir, filename)
	if (!file.exists()) throw IllegalArgumentException("Keystore file not found: ${file.absolutePath}")

	file.inputStream().use { keyStore.load(it, password.toCharArray()) }
	return keyStore
}

// TODO: Refactor this config.
class RelayConfig {

	val port: Int

	val secure: Boolean
	val keyStoreFile: String
	val keyStoreName: String
	val keyStorePassword: String

	val networkGameAddress: String
	val networkGamePort: Int

	val networkChatAddress: String
	val networkChatPort: Int

	init {
		val config = Utils.file(Utils.userDir, "config.properties")
		if (!config.exists()) {
			javaClass.getResourceAsStream("/config.properties")?.use { input ->
				Files.copy(input, config.toPath(), StandardCopyOption.REPLACE_EXISTING)
			} ?: throw IllegalStateException("Failed to copy default config.properties file.")
		}

		val properties = Properties()
		properties.load(config.inputStream())

		this.port = properties.getProperty("general.port").toInt()
		this.secure = properties.getProperty("general.secure").toBoolean()
		this.keyStoreFile = properties.getProperty("general.key-store-file")
		this.keyStoreName = properties.getProperty("general.key-store-name")
		this.keyStorePassword = properties.getProperty("general.key-store-password")

		this.networkGameAddress = properties.getProperty("network.game-server.address")
		this.networkGamePort = properties.getProperty("network.game-server.port").toInt()

		this.networkChatAddress = properties.getProperty("network.chat-server.address")
		this.networkChatPort = properties.getProperty("network.chat-server.port").toInt()
	}

}