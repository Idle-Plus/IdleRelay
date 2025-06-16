package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class WeaponType {
	None,
	Normal,
	Refined,
	Great,
	Elite,
	Superior,
	Outstanding,
	Godlike,
	Otherworldly,
}