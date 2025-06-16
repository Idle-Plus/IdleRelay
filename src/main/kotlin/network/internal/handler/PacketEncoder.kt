package dev.uraxys.idleclient.network.internal.handler

import dev.uraxys.idleclient.network.internal.WatsonPacket
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import kotlin.experimental.or

class PacketEncoder : MessageToByteEncoder<WatsonPacket>() {

	companion object {
		private val log = LogUtils.create(PacketEncoder::class)
	}

	override fun encode(
		ctx: ChannelHandlerContext,
		msg: WatsonPacket,
		output: ByteBuf
	) {

		val buf: ByteBuf = Unpooled.buffer()

		try {
			var flags: Byte = 0
			if (msg.preSharedKey != null) flags = flags or 0x01
			// Skipping metadata, aka 0x02.
			if (msg.ticksExpired != -1L) flags = flags or 0x04
			if (msg.senderId != null) flags = flags or 0x08

			// Write the packet "metadata".
			buf.writeByte(flags.toInt())
			buf.writeLongLE(if (msg.payload != null) msg.payload.length.toLong() else 0)
			if (msg.preSharedKey != null) buf.writeBytes(msg.preSharedKey)
			buf.writeIntLE(msg.status.ordinal)
			buf.writeBoolean(msg.syncRequest)
			buf.writeBoolean(msg.syncResponse)
			buf.writeLongLE(msg.ticksSent)
			if (msg.ticksExpired != -1L) buf.writeLongLE(msg.ticksExpired)
			buf.writeBytes(msg.conversationId)
			if (msg.senderId != null) buf.writeBytes(msg.senderId)

			// Get the written size.
			val writtenSize = buf.readableBytes()

			// Write the payload if it exists.
			if (msg.payload != null) buf.writeCharSequence(msg.payload, Charsets.UTF_8)

			// Write the size of the metadata and the data itself to the output buffer.
			output.writeIntLE(writtenSize)
			output.writeBytes(buf)
		} catch (e: Exception) {
			log.error("Failed to encode packet", e)
			ctx.close()
		} finally {
			buf.release()
		}
	}
}