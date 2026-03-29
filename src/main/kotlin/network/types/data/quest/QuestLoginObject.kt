package dev.uraxys.idleclient.network.types.data.quest

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /IdleMMOServer/Scripts/Shared/Quests

@ClientData
data class QuestLoginObject(
	val availableDailyQuestCount: Int,
	val availableWeeklyQuestCount: Int,
	val hasAvailableQuests: Boolean,
)
