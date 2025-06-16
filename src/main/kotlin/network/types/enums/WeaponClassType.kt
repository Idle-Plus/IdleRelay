package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class WeaponClassType {
	None,
	Scimitar,
	Longsword,
	Club,
	BattleAxe,
	HeavyHammer,
	Bow,
	Crossbow,
	Staff,
	Spear,
}