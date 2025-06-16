package dev.uraxys.idleclient.network.internal.handler

import dev.uraxys.idleclient.network.internal.WatsonPacket
import dev.uraxys.idleclient.network.internal.WatsonStatus
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import kotlin.experimental.and

class PacketDecoder : ByteToMessageDecoder() {

	companion object {
		private val log = LogUtils.create(PacketDecoder::class)
	}

	override fun decode(
		ctx: ChannelHandlerContext,
		input: ByteBuf,
		output: MutableList<Any?>
	) {
		try {
			// The first 4 bytes is the packet size.
			if (input.readableBytes() < 4) return

			// Mark the current reader index, in case we don't have enough bytes.
			input.markReaderIndex()

			// The packet size.
			val size = input.readIntLE()

			// Check if we got the full packet or not.
			if (input.readableBytes() < size) {
				// Reset the reader index and return.
				input.resetReaderIndex()
				return
			}

			// Read the packet, most of it isn't useful for our use case.
			// We're mostly interested in the payload.

			val flags: Byte = input.readByte()

			val payloadSize: Long = input.readLongLE()
			val preSharedKey: ByteArray? = if ((flags and 0x01) != 0.toByte()) ByteArray(16) else null
			val status: WatsonStatus = WatsonStatus.fromId(input.readIntLE())
			val syncRequest: Boolean = input.readBoolean()
			val syncResponse: Boolean = input.readBoolean()
			val ticksSent: Long = input.readLongLE()
			val ticksExpired: Long = if ((flags and 0x04) != 0.toByte()) input.readLongLE() else -1

			val conversationId = ByteArray(16)
			input.readBytes(conversationId)

			val senderId: ByteArray? = if ((flags and 0x08) != 0.toByte()) ByteArray(16) else null
			@Suppress("KotlinConstantConditions") // ¯\_(ツ)_/¯
			if (senderId != null) input.readBytes(senderId)

			// Check if we have a payload, if we do, make sure we got all the bytes.
			if (payloadSize > 0 && input.readableBytes() < payloadSize) {
				// Reset the reader index and return.
				input.resetReaderIndex()
				return
			}

			// Read the payload if the packet contains one.
			val payload: String? =
				if (payloadSize > 0) input.readCharSequence(payloadSize.toInt(), Charsets.UTF_8).toString()
				else null

			// Create the packet and add it to the output list.
			val packet = WatsonPacket(
				preSharedKey, status, syncRequest, syncResponse, ticksSent, ticksExpired,
				conversationId, senderId, payload
			)

			output.add(packet)
		} catch (e: Exception) {
			log.error("Failed to decode packet", e)
			ctx.close()
		}
	}
}