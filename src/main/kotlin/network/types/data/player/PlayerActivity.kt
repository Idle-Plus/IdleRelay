package dev.uraxys.idleclient.network.types.data.player

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class PlayerActivity(
	val type: PlayerActivityType,
	val taskType: Int,
	val activityIdentifierId: Int,
	val startTime: Instant?,
)
