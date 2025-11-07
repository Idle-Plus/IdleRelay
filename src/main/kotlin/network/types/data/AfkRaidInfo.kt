package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.network.types.enums.RaidType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import java.time.Instant

@ClientData
data class AfkRaidInfo(
	val raidType: RaidType,
	val startTimeUtc: Instant,
	val waveMilestoneRecord: WaveMilestoneRecord?
)
