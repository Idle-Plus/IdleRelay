package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class GuildInvitation(
	val guildName: String?,
	val guildId: String?,
	val date: Instant
)