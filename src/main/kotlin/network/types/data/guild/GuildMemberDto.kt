package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.enums.GameMode
import dev.uraxys.idleclient.network.types.enums.GuildRank
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class GuildMemberDto(
	val username: String,
	val rank: GuildRank,
	val activeServerId: String?,
	val hasVaultAccess: Boolean,
	val gameMode: GameMode,
	val isPremium: Boolean,
	val isGilded: Boolean,
	val logoutTime: Instant?,
	val joinDate: Instant,
)
