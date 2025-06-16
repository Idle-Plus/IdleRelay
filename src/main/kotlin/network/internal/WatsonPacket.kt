package dev.uraxys.idleclient.network.internal

import java.util.UUID

data class WatsonPacket(
	val preSharedKey: ByteArray?,
	val status: WatsonStatus,
	val syncRequest: Boolean,
	val syncResponse: Boolean,
	val ticksSent: Long,
	val ticksExpired: Long,
	val conversationId: ByteArray,
	val senderId: ByteArray?,
	val payload: String?
) {

	companion object {
		fun withPayload(payload: String): WatsonPacket {
			val conversationId = UUID.randomUUID()
			val conversationArray = ByteArray(16)

			// UUID to byte array
			val mostSigBits = conversationId.mostSignificantBits
			val leastSigBits = conversationId.leastSignificantBits
			for (i in 0..7) {
				conversationArray[i] = (mostSigBits shr (i * 8)).toByte()
				conversationArray[i + 8] = (leastSigBits shr (i * 8)).toByte()
			}

			return WatsonPacket(
				preSharedKey = null,
				status = WatsonStatus.NORMAL,
				syncRequest = false,
				syncResponse = false,
				ticksSent = System.currentTimeMillis() * 10_000L,
				ticksExpired = -1,
				conversationId = conversationArray,
				senderId = null,
				payload = payload
			)
		}

		fun withStatus(status: WatsonStatus): WatsonPacket {
			val conversationId = UUID.randomUUID()
			val conversationArray = ByteArray(16)

			// UUID to byte array
			val mostSigBits = conversationId.mostSignificantBits
			val leastSigBits = conversationId.leastSignificantBits
			for (i in 0..7) {
				conversationArray[i] = (mostSigBits shr (i * 8)).toByte()
				conversationArray[i + 8] = (leastSigBits shr (i * 8)).toByte()
			}

			return WatsonPacket(
				preSharedKey = null,
				status = status,
				syncRequest = false,
				syncResponse = false,
				ticksSent = System.currentTimeMillis() * 10_000L,
				ticksExpired = -1,
				conversationId = conversationArray,
				senderId = null,
				payload = null
			)
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as WatsonPacket

		if (syncRequest != other.syncRequest) return false
		if (syncResponse != other.syncResponse) return false
		if (ticksSent != other.ticksSent) return false
		if (ticksExpired != other.ticksExpired) return false
		if (!preSharedKey.contentEquals(other.preSharedKey)) return false
		if (status != other.status) return false
		if (!conversationId.contentEquals(other.conversationId)) return false
		if (!senderId.contentEquals(other.senderId)) return false
		if (payload != other.payload) return false

		return true
	}

	override fun hashCode(): Int {
		var result = syncRequest.hashCode()
		result = 31 * result + syncResponse.hashCode()
		result = 31 * result + ticksSent.hashCode()
		result = 31 * result + ticksExpired.hashCode()
		result = 31 * result + (preSharedKey?.contentHashCode() ?: 0)
		result = 31 * result + status.hashCode()
		result = 31 * result + conversationId.contentHashCode()
		result = 31 * result + (senderId?.contentHashCode() ?: 0)
		result = 31 * result + (payload?.hashCode() ?: 0)
		return result
	}
}

enum class WatsonStatus {
	NORMAL,
	SUCCESS,
	FAILURE,
	AUTH_REQUIRED,
	AUTH_REQUESTED,
	AUTH_SUCCESS,
	AUTH_FAILURE,
	REMOVED,
	SHUTDOWN,
	HEARTBEAT,
	TIMEOUT,
	REGISTER;

	companion object {
		private val values = entries.toTypedArray()

		fun fromId(value: Int): WatsonStatus {
			if (value < 0 || value >= this.values.size)
				throw IllegalArgumentException("Invalid WatsonStatus value: $value")
			return this.values[value]
		}
	}
}