package dev.uraxys.idleclient.network.types.data.skill

import dev.uraxys.idleclient.network.types.data.skill.Skill
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class ExpDelta(
	val skill: Skill,
	val amount: Int
)
