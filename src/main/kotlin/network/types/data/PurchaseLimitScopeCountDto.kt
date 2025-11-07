package dev.uraxys.idleclient.network.types.data

import dev.uraxys.idleclient.network.types.enums.PurchaseLimitScope
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class PurchaseLimitScopeCountDto(
	val scope: PurchaseLimitScope,
	val used: Int
)
