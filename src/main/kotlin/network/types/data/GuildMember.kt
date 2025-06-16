package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.network.types.enums.GameMode
import dev.uraxys.idleclient.network.types.enums.GuildRank
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class GuildMember(
	val rank: GuildRank = GuildRank.member,
	val isOnline: Boolean,
	val hasVaultAccess: Boolean,
	val gameMode: GameMode = GameMode.NotSelected,
	val isPremium: Boolean,
	val isPremiumPlus: Boolean,
	val logoutTime: Instant?,
	val joinDate: Instant?,
)