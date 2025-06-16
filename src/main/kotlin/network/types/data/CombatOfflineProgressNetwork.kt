package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class CombatOfflineProgressNetwork(
	val receivedLoot: Map<Int, Int>?,
	val consumedFood: Map<Int, Int>?,
	val experiencesReceived: Map<Byte, Int>?,
	val levelsReceived: Map<Byte, Int>?,
	val monsterKillsByTaskId: Map<Int, Int>?,
	val exterminatingPointsReceived: Int,
	val playerDied: Boolean,
	val timeOfDeathMilliseconds: Double,
	val taskId: Byte,
	val usedAmmoAmount: Int,
	val combatEnded: Boolean,
)