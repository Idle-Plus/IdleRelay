package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

/*
 * Original class name: StateChangeMessage
 * Changed to: GuildStateChangeMessage
 */

@ClientData
data class GuildStateChangeMessage(
	val localizationKey: String,
	val args: Array<String>,
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as GuildStateChangeMessage

		if (localizationKey != other.localizationKey) return false
		if (!args.contentEquals(other.args)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = localizationKey.hashCode()
		result = 31 * result + args.contentHashCode()
		return result
	}
}
