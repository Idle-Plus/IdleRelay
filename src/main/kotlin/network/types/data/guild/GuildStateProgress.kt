package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.data.skill.Skill
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildStateProgress(
	val experiences: Map<Skill, Float>?
)
