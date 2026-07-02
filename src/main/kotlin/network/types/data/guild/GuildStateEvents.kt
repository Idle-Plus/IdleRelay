package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.data.skill.Skill
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildStateEvents(
	val serialized: String?,
	val skillingTickets: Map<Skill, Int>?,
	val skillingPartyCompletions: Int?,
)
