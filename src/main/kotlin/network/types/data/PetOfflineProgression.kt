package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.network.types.data.skill.Skill
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class PetOfflineProgression(
	val skill: Skill = Skill.None,
	val taskId: Int,
	val experienceReceived: Float,
	val itemsReceived: Map<Int, Int>,
	val itemsLost: Map<Int, Int>,
	val oldLevel: Byte,
	val newLevel: Byte,
)