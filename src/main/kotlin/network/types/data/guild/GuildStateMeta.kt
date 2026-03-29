package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class GuildStateMeta(
	val guildName: String?,
	val tag: String?,
	val recruitmentMessage: String?,
	val creationDate: Instant?,
	val earliestPossibleDeletionDate: Instant?,
	val recentlyCreated: Boolean?
)
