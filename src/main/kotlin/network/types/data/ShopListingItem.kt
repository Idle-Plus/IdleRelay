package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class ShopListingItem(
	val itemId: Int,
	val amount: Int,
	val price: Int
)