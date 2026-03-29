package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class GuildStateQuests(
	val nextQuestGenerationTimestamp: Instant?,
	val dailySkillingQuests: List<DailyGuildQuest>?,
	val dailyCombatQuests: List<DailyGuildQuest>?,
	val skillingContributors: List<String>?,
	val combatContributors: List<String>?,
)
