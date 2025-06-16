package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.data.ActiveExterminatingAssignment
import dev.uraxys.idleclient.network.types.data.CombatOfflineProgressNetwork
import dev.uraxys.idleclient.network.types.data.DailyGuildQuest
import dev.uraxys.idleclient.network.types.data.GuildApplicationForLogin
import dev.uraxys.idleclient.network.types.data.GuildInvitation
import dev.uraxys.idleclient.network.types.data.GuildMember
import dev.uraxys.idleclient.network.types.data.PetOfflineProgression
import dev.uraxys.idleclient.network.types.data.ShopListingItem
import dev.uraxys.idleclient.network.types.data.SkillingOfflineProgressNetwork
import dev.uraxys.idleclient.network.types.enums.AttackStyle
import dev.uraxys.idleclient.network.types.enums.ExterminatingShopUnlockType
import dev.uraxys.idleclient.network.types.enums.FTUEStage
import dev.uraxys.idleclient.network.types.enums.GameMode
import dev.uraxys.idleclient.network.types.enums.PlayerRewardType
import dev.uraxys.idleclient.network.types.enums.PotionType
import dev.uraxys.idleclient.network.types.enums.Skill
import dev.uraxys.idleclient.network.types.enums.UpgradeType
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import java.time.Instant

@InternalPacket(1)
class LoginDataMessage(
	msgType: Int,

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
	val combatStyle: Byte,
	val archeryCombatStyle: Byte,
	val magicCombatStyle: Byte,
	val autoEatPercentage: Byte,
	val usedBossKey: Int,
	val kronosAttackStyleWeakness: AttackStyle = AttackStyle.None,
	val tutorialStage: FTUEStage?,
	val gameMode: GameMode?,
	val configVersion: Int,
	val guildName: String?,
	val members: Map<String, GuildMember>?,
	val activeGuildApplications: List<GuildApplicationForLogin>?,
	val vaultGold: Double,
	val guildHouseId: Int,
	val clanCredits: Int,
	val nextQuestGenerationTimestamp: Instant?,
	val dailySkillingQuests: List<DailyGuildQuest>?,
	val dailyCombatQuests: List<DailyGuildQuest>?,
	val skillingContributors: List<String>?,
	val combatContributors: List<String>?,
	val unlockedUpgrades: List<UpgradeType>?,
	val accumulatedCredits: Int,
	val offlineHours: Byte,
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
	val guildInvitations: Array<GuildInvitation>?,
	val useInventoryConsumables: Boolean,
	val shouldShowQuestsNotification: Boolean,
	val questerUnlocked: Boolean,
	val petOfflineProgress: PetOfflineProgression?,
	val activePetSkill: Skill?,
	val petTaskId: Byte,
	val itemsInWithdrawalBox: Boolean,
	val exterminatingPoints: Int,
	val activeExterminatingAssignment: ActiveExterminatingAssignment?,
	val exterminatorUnlocked: Boolean,
	val unlockedExterminatingPurchases: List<ExterminatingShopUnlockType>?,
	val playerRewards: Map<PlayerRewardType, Int>?
) : NetworkMessage(msgType) {
	override fun toString(): String {
		return "LoginDataMessage(username=$username, skillExperiencesJson=$skillExperiencesJson, inventoryJson=$inventoryJson, gold=$gold, equipmentJson=$equipmentJson, equippedAmmunitionAmount=$equippedAmmunitionAmount, newPlayer=$newPlayer, health=$health, isVerified=$isVerified, premiumEndDate=$premiumEndDate, isPremiumPlus=$isPremiumPlus, unlockedBossHunter=$unlockedBossHunter, unlockedAutoLoadouts=$unlockedAutoLoadouts, upgrades=$upgrades, combatStyle=$combatStyle, archeryCombatStyle=$archeryCombatStyle, magicCombatStyle=$magicCombatStyle, autoEatPercentage=$autoEatPercentage, usedBossKey=$usedBossKey, kronosAttackStyleWeakness=$kronosAttackStyleWeakness, tutorialStage=$tutorialStage, gameMode=$gameMode, configVersion=$configVersion, guildName=$guildName, members=$members, activeGuildApplications=$activeGuildApplications, vaultGold=$vaultGold, guildHouseId=$guildHouseId, clanCredits=$clanCredits, nextQuestGenerationTimestamp=$nextQuestGenerationTimestamp, dailySkillingQuests=$dailySkillingQuests, dailyCombatQuests=$dailyCombatQuests, skillingContributors=$skillingContributors, combatContributors=$combatContributors, unlockedUpgrades=$unlockedUpgrades, accumulatedCredits=$accumulatedCredits, offlineHours=$offlineHours, skillingOfflineProgress=$skillingOfflineProgress, combatOfflineProgress=$combatOfflineProgress, itemsSoldOffline=${itemsSoldOffline.contentToString()}, serializedPlayerToggleableSettings=$serializedPlayerToggleableSettings, adsWatchedToday=$adsWatchedToday, lastAdWatchedTimestampTicks=$lastAdWatchedTimestampTicks, adBoostedSeconds=$adBoostedSeconds, adBoostPaused=$adBoostPaused, purchasedInventorySlots=$purchasedInventorySlots, clanVaultSpacePurchased=$clanVaultSpacePurchased, activePotionEffects=$activePotionEffects, serializedItemEnchantments=$serializedItemEnchantments, guildInvitations=${guildInvitations.contentToString()}, useInventoryConsumables=$useInventoryConsumables, shouldShowQuestsNotification=$shouldShowQuestsNotification, questerUnlocked=$questerUnlocked, petOfflineProgress=$petOfflineProgress, activePetSkill=$activePetSkill, petTaskId=$petTaskId, itemsInWithdrawalBox=$itemsInWithdrawalBox, exterminatingPoints=$exterminatingPoints, activeExterminatingAssignment=$activeExterminatingAssignment, exterminatorUnlocked=$exterminatorUnlocked, unlockedExterminatingPurchases=$unlockedExterminatingPurchases, playerRewards=$playerRewards)"
	}
}