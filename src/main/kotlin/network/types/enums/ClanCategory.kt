package dev.uraxys.idleclient.network.types.enums

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
enum class ClanCategory {
	None,
	Casual,
	Competitive,
	Hardcore
}