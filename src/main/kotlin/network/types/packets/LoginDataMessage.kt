package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.data.ActiveExterminatingAssignment
import dev.uraxys.idleclient.network.types.data.AfkRaidInfo
import dev.uraxys.idleclient.network.types.data.CombatOfflineProgressNetwork
import dev.uraxys.idleclient.network.types.data.guild.GuildInvitation
import dev.uraxys.idleclient.network.types.data.PetOfflineProgression
import dev.uraxys.idleclient.network.types.data.PurchaseLimitScopeCountDto
import dev.uraxys.idleclient.network.types.data.PvmBestRecord
import dev.uraxys.idleclient.network.types.data.ShopListingItem
import dev.uraxys.idleclient.network.types.data.SkillingOfflineProgressNetwork
import dev.uraxys.idleclient.network.types.data.UpgradeType
import dev.uraxys.idleclient.network.types.data.event.HolidayEvent
import dev.uraxys.idleclient.network.types.data.guild.GuildEventLobbyState
import dev.uraxys.idleclient.network.types.data.quest.QuestLoginObject
import dev.uraxys.idleclient.network.types.enums.AttackStyle
import dev.uraxys.idleclient.network.types.enums.ExterminatingShopUnlockType
import dev.uraxys.idleclient.network.types.enums.FTUEStage
import dev.uraxys.idleclient.network.types.enums.GameMode
import dev.uraxys.idleclient.network.types.enums.PlayerRewardType
import dev.uraxys.idleclient.network.types.data.PotionType
import dev.uraxys.idleclient.network.types.enums.PvmStatType
import dev.uraxys.idleclient.network.types.enums.RaidType
import dev.uraxys.idleclient.network.types.data.skill.Skill
import dev.uraxys.idleclient.network.types.packets.clan.ReceiveGuildStateMessage
import dev.uraxys.idleclient.tools.typescript.annotations.ClientType
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import java.time.Instant

@InternalPacket(GamePacket.LoginDataMessage)
class LoginDataMessage(
	//msgType: Int,

	val username: String?,
	val skillExperiencesJson: String?,
	val inventoryJson: String?,
	val gold: Double,
	val equipmentJson: String?,
	val equippedAmmunitionAmount: Int,
	val newPlayer: Boolean,
	val health: Int,
	val isVerified: Boolean,
	val premiumEndDate: Instant?,
	val isPremiumPlus: Boolean,
	val unlockedBossHunter: Boolean,
	val unlockedAutoLoadouts: Boolean,
	val upgrades: Map<UpgradeType, Int>?,
	val disabledUpgrades: List<UpgradeType>, // TODO: nullable?
	val combatStyle: Byte,
	val archeryCombatStyle: Byte,
	val magicCombatStyle: Byte,
	val autoEatPercentage: Byte,
	val usedBossKey: Int,
	val kronosAttackStyleWeakness: AttackStyle = AttackStyle.None,
	val tutorialStage: FTUEStage?,
	val gameMode: GameMode?,
	val configVersion: Int,
	//val guildName: String?,
	//val members: Map<String, GuildMember>?,
	/*val activeGuildApplications: List<GuildApplicationForLogin>?,
	val vaultGold: Double,
	val guildHouseId: Int,
	val clanCredits: Int,
	val nextQuestGenerationTimestamp: Instant?,
	val dailySkillingQuests: List<DailyGuildQuest>?,
	val dailyCombatQuests: List<DailyGuildQuest>?,
	val skillingContributors: List<String>?,
	val combatContributors: List<String>?,
	val unlockedUpgrades: List<UpgradeType>?,
	val accumulatedCredits: Int,*/
	val guildStateMessage: @ClientType("ReceiveGuildStateMessage") ReceiveGuildStateMessage?,
	val guildLobbyStates: List<GuildEventLobbyState>?,
	//val offlineHours: Byte,
	val offlineTime: String, // C# TimeSpan
	val skillingOfflineProgress: SkillingOfflineProgressNetwork?,
	val combatOfflineProgress: CombatOfflineProgressNetwork?,
	val itemsSoldOffline: Array<ShopListingItem>?,
	val serializedPlayerToggleableSettings: String?,
	val adsWatchedToday: Byte,
	val lastAdWatchedTimestampTicks: Long,
	val adBoostedSeconds: Int,
	val adBoostPaused: Boolean,
	val purchasedInventorySlots: Int,
	val clanVaultSpacePurchased: Int,
	val activePotionEffects: Map<PotionType, Int>?,
	val serializedItemEnchantments: String?,
	//val guildInvitations: Array<GuildInvitation>?,
	val guildInvitations: List<GuildInvitation>?,
	val useInventoryConsumables: Boolean,
	//val shouldShowQuestsNotification: Boolean,
	val questLoginObject: QuestLoginObject,
	val questerUnlocked: Boolean,
	val petOfflineProgress: PetOfflineProgression?,
	val activePetSkill: Skill?,
	val petTaskId: Byte,
	val itemsInWithdrawalBox: Boolean,
	val exterminatingPoints: Int,
	val activeExterminatingAssignment: ActiveExterminatingAssignment?,
	val exterminatorUnlocked: Boolean,
	val unlockedExterminatingPurchases: List<ExterminatingShopUnlockType>?,
	val playerRewards: Map<PlayerRewardType, Int>?,
	val stats: Map<PvmStatType, Int>,
	val pvmBestTimes: Map<PvmStatType, PvmBestRecord>?,
	val afkRaidInfo: AfkRaidInfo?,
	val unlockedAFKRaids: List<RaidType>,
	val purchaseLimitCounts: Array<PurchaseLimitScopeCountDto>,
	val serverId: String,
	val activeHolidayEvent: HolidayEvent,
	val invocationCofferGold: Double, // Decimal
	val invocationRitualPower: Long,
	val isInGuild: Boolean,
) : NetworkMessage() {
	override fun toString(): String {
		return "LoginDataMessage(username=$username, skillExperiencesJson=$skillExperiencesJson, inventoryJson=$inventoryJson, gold=$gold, equipmentJson=$equipmentJson, equippedAmmunitionAmount=$equippedAmmunitionAmount, newPlayer=$newPlayer, health=$health, isVerified=$isVerified, premiumEndDate=$premiumEndDate, isPremiumPlus=$isPremiumPlus, unlockedBossHunter=$unlockedBossHunter, unlockedAutoLoadouts=$unlockedAutoLoadouts, upgrades=$upgrades, disabledUpgrades=$disabledUpgrades, combatStyle=$combatStyle, archeryCombatStyle=$archeryCombatStyle, magicCombatStyle=$magicCombatStyle, autoEatPercentage=$autoEatPercentage, usedBossKey=$usedBossKey, kronosAttackStyleWeakness=$kronosAttackStyleWeakness, tutorialStage=$tutorialStage, gameMode=$gameMode, configVersion=$configVersion, guildStateMessage=$guildStateMessage, guildLobbyStates=$guildLobbyStates, offlineTime='$offlineTime', skillingOfflineProgress=$skillingOfflineProgress, combatOfflineProgress=$combatOfflineProgress, itemsSoldOffline=${itemsSoldOffline.contentToString()}, serializedPlayerToggleableSettings=$serializedPlayerToggleableSettings, adsWatchedToday=$adsWatchedToday, lastAdWatchedTimestampTicks=$lastAdWatchedTimestampTicks, adBoostedSeconds=$adBoostedSeconds, adBoostPaused=$adBoostPaused, purchasedInventorySlots=$purchasedInventorySlots, clanVaultSpacePurchased=$clanVaultSpacePurchased, activePotionEffects=$activePotionEffects, serializedItemEnchantments=$serializedItemEnchantments, guildInvitations=$guildInvitations, useInventoryConsumables=$useInventoryConsumables, questLoginObject=$questLoginObject, questerUnlocked=$questerUnlocked, petOfflineProgress=$petOfflineProgress, activePetSkill=$activePetSkill, petTaskId=$petTaskId, itemsInWithdrawalBox=$itemsInWithdrawalBox, exterminatingPoints=$exterminatingPoints, activeExterminatingAssignment=$activeExterminatingAssignment, exterminatorUnlocked=$exterminatorUnlocked, unlockedExterminatingPurchases=$unlockedExterminatingPurchases, playerRewards=$playerRewards, stats=$stats, pvmBestTimes=$pvmBestTimes, afkRaidInfo=$afkRaidInfo, unlockedAFKRaids=$unlockedAFKRaids, purchaseLimitCounts=${purchaseLimitCounts.contentToString()}, serverId='$serverId', activeHolidayEvent=$activeHolidayEvent, invocationCofferGold=$invocationCofferGold, invocationRitualPower=$invocationRitualPower, isInGuild=$isInGuild)"
	}
}