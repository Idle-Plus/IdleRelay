package dev.uraxys.idleclient.network.types.data.item

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class ItemDelta(
	val itemId: Int,
	val amount: Int
)
