package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.enums.UpgradeType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildStateEconomy(
	val purchasedVaultSlots: Int?,
	val clanCredits: Int?,
	val accumulatedCredits: Int?,
	val invokingPlayerHasClaimableLoot: Boolean?,
	val unlockedUpgrades: List<UpgradeType>?,
)
