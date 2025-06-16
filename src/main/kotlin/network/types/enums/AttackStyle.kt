package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class AttackStyle {
	None,
	Stab,
	Slash,
	Pound,
	Crush,
	Archery,
	Magic,
	RandomizeOnCombatStart,
	ApplyAll,
}