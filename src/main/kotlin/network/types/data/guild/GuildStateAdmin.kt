package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.network.types.enums.ClanCategory
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildStateAdmin(
	val isRecruiting: Boolean?,
	val category: ClanCategory?,
	val primaryLanguage: String?,
	val minimumTotalLevelRequired: Int?,
	val activeGuildApplications: List<GuildApplicationData>?,
)
