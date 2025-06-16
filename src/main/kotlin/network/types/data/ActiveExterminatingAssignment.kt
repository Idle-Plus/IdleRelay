package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class ActiveExterminatingAssignment(
	val assigningExpertId: Int,
	val monsterName: String?,
	val killsRequired: Int,
	val killsAcquired: Int,
)