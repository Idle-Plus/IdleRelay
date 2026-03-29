package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /Guilds/UI

@ClientData
enum class ClanEventType {
	None,
	Gathering,
	Crafting,
	CombatBigExpDaily,
	CombatBigLootDaily,
	SkillingParty,
}