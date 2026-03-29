package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class DailyGuildQuest(
	val entityId: Int,
	val fullAmountRequired: Int,
	val amountContributed: Int,
	val type: TaskType,
	val isCompleted: Boolean,
)