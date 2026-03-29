package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /Guilds/UI

@ClientData
data class GuildEventLobbyState(
	val eventType: ClanEventType,
	val eventIsCurrentlyRunning: Boolean,
	val membersInParty: Byte,
)
