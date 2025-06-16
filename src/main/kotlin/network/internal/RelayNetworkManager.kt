package dev.uraxys.idleclient.network.internal

import dev.uraxys.idleclient.network.ServerType
import dev.uraxys.idleclient.network.internal.handler.PacketDecoder
import dev.uraxys.idleclient.network.internal.handler.PacketEncoder
import dev.uraxys.idleclient.network.web.NetworkClient
import dev.uraxys.idleclient.utils.extensions.coroutineAwait
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

class RelayNetworkManager(
	private val address: String,
	private val port: Int,
	private val type: ServerType,
) {

	private val group: NioEventLoopGroup = NioEventLoopGroup()

	fun dispose() {
		this.group.shutdownGracefully()
	}

	suspend fun connect(client: NetworkClient): Boolean {
		val bootstrap = Bootstrap().group(this.group)
			.channel(NioSocketChannel::class.java)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(object : ChannelInitializer<SocketChannel>() {
				override fun initChannel(ch: SocketChannel) {
					// Create the connection and add it to the pipeline.
					client.game = RelayConnection(client, type) // TODO: Currently only sets the game connection.
					log.info("NetworkClient assigned a new relay connection to ${type}.")

					ch.pipeline()
						// Inbound - v
						.addLast("decoder", PacketDecoder())
						.addLast("handler", client.game) // TODO: Currently only sets the game connection.
						.addLast("encoder", PacketEncoder())
						// Outbound - ^
				}
			})

		try {
			// TODO: Do something with the channel?
			val channel = bootstrap.connect(this.address, this.port)
				.coroutineAwait().channel()
			log.info("Connected NetworkClient ${client.networkId} to ${this.type} server.")
			return true
		} catch (e: Exception) {
			currentCoroutineContext().ensureActive()
			log.error("Failed to connect NetworkClient ${client.networkId} to ${this.type} server.", e)
			return false
		}
	}

	companion object {
		private val log = LogUtils.create(RelayNetworkManager::class)
	}
}