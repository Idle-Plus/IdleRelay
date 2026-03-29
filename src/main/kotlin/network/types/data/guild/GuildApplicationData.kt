package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildApplicationData(
	val applicantName: String,
	val message: String,
	val totalLevelAtTimeOfApplication: Int,
)
