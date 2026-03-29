package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildStateMembers(
	val members: Array<GuildMemberDto>?
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as GuildStateMembers

		if (!members.contentEquals(other.members)) return false

		return true
	}

	override fun hashCode(): Int {
		return members?.contentHashCode() ?: 0
	}
}
