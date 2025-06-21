package dev.uraxys.idleclient.network.internal

import dev.uraxys.idleclient.network.ServerType
import dev.uraxys.idleclient.network.web.NetworkClient
import dev.uraxys.idleclient.utils.utils.Common
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class RelayConnection(
	val client: NetworkClient,
	val type: ServerType,
) : SimpleChannelInboundHandler<WatsonPacket>() {

	companion object {
		private val log = LogUtils.create(RelayConnection::class)
	}

	private var disconnected: Boolean = false
	private var channel: Channel? = null

	/*
	 * Implementation
	 */

	fun ping() {
		if (this.disconnected) return
		if (this.channel == null) return
		// The client sends an empty packet every 30 seconds, and I'm guessing
		// this is some custom behavior, as the status is set to normal.
		this.channel!!.writeAndFlush(WatsonPacket.withStatus(WatsonStatus.NORMAL))
	}

	fun send(packet: WatsonPacket, skipLog: Boolean = false) {
		if (this.disconnected) return
		if (this.channel == null) return

		if (!skipLog) {
			log.info("< Sending packet in session ${this.client.networkId}: ${packet.payload}")
		}

		this.channel!!.writeAndFlush(packet)
	}

	fun disconnect() {
		if (this.disconnected) return
		this.disconnected = true
		if (this.channel == null) return

		this.channel!!
			.writeAndFlush(WatsonPacket.withStatus(WatsonStatus.SHUTDOWN))
			.addListener { this.channel?.close() }

	}

	/*
	 * Netty
	 */

	override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
		if (this.disconnected) return
		log.error("An exception occurred in the RelayConnection to ${this.type} for ${this.client.networkId}", cause)
		this.disconnect()
	}

	override fun channelActive(ctx: ChannelHandlerContext) {
		super.channelActive(ctx)
		this.channel = ctx.channel()

		// Notify the handler
		this.client.`$onConnectionOpen`(this.type)

		// Send the authentication packet
		val packet = Common.JSON.createObjectNode()
		packet.put("MsgType", 2)
		packet.put("SessionTicket", this.client.sessionTicket)
		packet.put("ClientVersion", this.client.clientVersion)
		packet.put("ConfigVersion", this.client.configVersion)
		packet.put("UniqueDeviceId", this.client.uniqueDeviceId)

		val payload = packet.toString()
		this.send(WatsonPacket.withPayload(payload), true)
	}

	override fun channelInactive(ctx: ChannelHandlerContext) {
		super.channelInactive(ctx)
		this.disconnected = true
		this.client.`$onConnectionClose`(this.type)
	}

	override fun channelRead0(ctx: ChannelHandlerContext, msg: WatsonPacket) {
		val payload = msg.payload
		if (payload == null) {
			log.info("Received ping type packet from server.")
			return
		}

		if (!this.client.authenticated) {
			this.client.`$onAuthMessage`(this.type, payload)
			return
		}

		this.client.`$onNetworkMessage`(this.type, payload)
	}
}