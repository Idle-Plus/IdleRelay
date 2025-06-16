package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class DailyGuildQuest(
	val entityId: Int,
	val fullAmountRequired: Int,
	val amountContributed: Int,
	val type: Int, // TODO: ???, I have no idea what this is, it isn't included
	               //       in the client code.
	val isCompleted: Boolean,
)