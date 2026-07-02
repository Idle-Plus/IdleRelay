package dev.uraxys.idleclient.network.types.data.item

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /IdleMMOServer/Scripts/Shared/Data/Items

@ClientData
enum class ItemEffectType {
	None,
	Flaming,
	Ghostly,
	Void,
	Nature,
}