package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class PvmBestRecord(
	val bestTimeMs: Long,
	val highestWave: Int?,
	val lastUpdatedUtc: Instant,
)