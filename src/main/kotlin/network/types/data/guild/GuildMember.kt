package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.enums.GameMode
import dev.uraxys.idleclient.network.types.enums.GuildRank
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

// path: /

@ClientData
data class GuildMember(
	val displayName: String,
	val rank: GuildRank = GuildRank.member,
	val serverId: String?,
	val hasVaultAccess: Boolean,
	val gameMode: GameMode = GameMode.NotSelected,
	val isPremium: Boolean,
	val isPremiumPlus: Boolean,
	val logoutTime: Instant?,
	val joinDate: Instant?,
	val isOnline: Boolean,
)