package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

// path: /

@ClientData
data class GuildInvitation(
	val guildName: String?,
	val guildId: String?,
	val date: Instant
)