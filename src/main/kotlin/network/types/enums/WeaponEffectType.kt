package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class WeaponEffectType {
	None,
	Flaming,
	Ghostly,
	Void,
	Nature,
}