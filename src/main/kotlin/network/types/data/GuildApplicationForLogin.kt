package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildApplicationForLogin(
	val applicantName: String,
	val message: String,
	val totalLevelAtTimeOfApplication: Int
)