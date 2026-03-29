package dev.uraxys.idleclient.network.types.data.player

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class PlayerActivityType {
	Unknown,
	Idle,
	Task,
	ClanEvent,
	ClanBoss,
	Raid,
	AFKRaid,
	InLobby,
}