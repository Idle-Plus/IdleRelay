package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class WaveMilestoneRecord(
	val wave: Int,
	val bestTimeMs: Long,
	val lastUpdatedUtc: Instant
)
