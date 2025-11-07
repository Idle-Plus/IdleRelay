package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.data.DailyGuildQuest
import dev.uraxys.idleclient.network.types.data.GuildMember
import dev.uraxys.idleclient.network.types.enums.Skill
import dev.uraxys.idleclient.network.types.enums.UpgradeType
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import java.time.Instant

@InternalPacket(38)
class PlayerJoinedGuildMessage(
	msgType: Int,

	// Sent to every online member.

	val playerJoining: String,
	val isPremium: Boolean,
	val isPremiumPlus: Boolean,
	val isOnline: Boolean,
	val logOutTime: Instant?,

	// Only sent to the joining player.

	val guildName: String?,
	val members: Map<String, GuildMember>?,
	val nextQuestGenerationTimestamp: Instant?,
	val dailySkillingQuests: List<DailyGuildQuest>?,
	val skillingContributors: List<String>?,
	val dailyCombatQuests: List<DailyGuildQuest>?,
	val combatContributors: List<String>?,
	val skillExperiences: Map<Skill, Float>?,
	val ownedHouseId: Int,
	val vault: Map<Int, Int>?,
	val gold: Double,
	val unlockedUpgrades: List<UpgradeType>?,
	val clanVaultSpacePurchased: Int,
	val credits: Int,
	val serializedEventStates: String?,
	val skillingTickets: Map<Skill, Int>?,
	val skillingPartyCompletions: Int,
	val tag: String?,
) : NetworkMessage(msgType)