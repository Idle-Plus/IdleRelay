package dev.uraxys.idleclient

import dev.uraxys.idleclient.network.PacketManager
import dev.uraxys.idleclient.network.ServerType
import dev.uraxys.idleclient.network.internal.RelayNetworkManager
import dev.uraxys.idleclient.network.web.NetworkManager
import dev.uraxys.idleclient.utils.SmartThreadPoolExecutor
import dev.uraxys.idleclient.utils.Utils
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.ktor.server.engine.EmbeddedServer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.util.concurrent.TimeUnit

@Suppress("FunctionName", "SameParameterValue")
private fun LogExceptionHandler(name: String): CoroutineExceptionHandler {
	return CoroutineExceptionHandler { _, e ->
		ClientRelay.log.error("Caught unhandled exception in $name context!", e) }
}

class ClientRelay(
	config: RelayConfig
) {

	var state: ServerState = ServerState.INACTIVE; private set

	val networkManager: NetworkManager = NetworkManager(this)

	val gameNetwork: RelayNetworkManager = RelayNetworkManager(config.networkGameAddress, config.networkGamePort, ServerType.GAME)
	val chatNetwork: RelayNetworkManager = RelayNetworkManager(config.networkChatAddress, config.networkChatPort, ServerType.CHAT)



	suspend fun start() {
		if (this.state != ServerState.INACTIVE) throw IllegalStateException("ClientRelay has already been started!")
		this.state = ServerState.INITIALIZING

		// TODO: Initialize - No idea what to initialize, a relay doesn't
		//       really do that much.
		//       It could be cool to add custom features though, for example
		//       pvp between players using the client, etc.
		PacketManager.initialize()

		log.info("IdleClient relay started.")
		this.state = ServerState.RUNNING
		this.run()
	}

	private fun run() {
		val tickRate = 1 // How many times to tick per second.
		val tickTime = (1000L / tickRate)
		var nextTick: Long

		var t: Long = 0
		val sample = 10 * 5
		val average = IntArray(sample)

		TICK_SCOPE.launch(CoroutineName("RelayTickLoop")) {
			while (state == ServerState.RUNNING) {
				try {
					val tick = Utils.getMillis()
					nextTick = tick + tickTime

					// Run the current tick.
					val start = Utils.getMillis()
					tick()
					val timeTaken = Utils.getMillis() - start

					// Wait until the next tick.
					while (Utils.getMillis() < nextTick) {
						yield()
						delay(1)
					}

					t++
					average[(t % sample).toInt()] = timeTaken.toInt()
					if (t % sample == 0L) {
						var sum = 0
						for (i in average) sum += i

						val ave = sum / average.size
						if (ave != 0) log.info("Tick time took ${ave}ms (${Thread.currentThread().name}).")
					}
				} catch (e: Exception) {
					log.error("Uncaught exception in relay loop!", e)
				}
			}
		}
	}

	private suspend fun tick() {
		this.networkManager.tick()
	}

	companion object {
		val log = LogUtils.create(ClientRelay::class)

		lateinit var SERVER: ClientRelay
		lateinit var WEB_SERVER: EmbeddedServer<*, *>

		// Network
		private val NETWORK_THREAD_POOL = SmartThreadPoolExecutor(1, 3, 60L, TimeUnit.SECONDS)
		private val NETWORK_DISPATCHER = NETWORK_THREAD_POOL.asCoroutineDispatcher()
		val NETWORK_SCOPE = CoroutineScope(this.NETWORK_DISPATCHER + SupervisorJob() + LogExceptionHandler("network"))

		// Tick
		private val TICK_THREAD_POOL = SmartThreadPoolExecutor(1, 2, 60L, TimeUnit.SECONDS)
		private val TICK_DISPATCHER = TICK_THREAD_POOL.asCoroutineDispatcher()
		val TICK_SCOPE = CoroutineScope(this.TICK_DISPATCHER + SupervisorJob() + LogExceptionHandler("tick"))
	}
}

enum class ServerState {
	INACTIVE,
	INITIALIZING,
	RUNNING,
	STOPPING,
	STOPPED
}