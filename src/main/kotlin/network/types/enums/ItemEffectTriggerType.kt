package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class ItemEffectTriggerType {
	None,
	Lifesteal,
	DoubleLoot,
}