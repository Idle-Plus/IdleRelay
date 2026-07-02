package dev.uraxys.idleclient.network.types.data.storage

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

// path: /IdleMMOServer/Scripts/Shared/Content/Storage

@ClientData
enum class StorageType {
	None,
	Inventory,
	InvocationCoffer,
}