package dev.uraxys.idleclient.network.types.data.guild

import dev.uraxys.idleclient.tools.typescript.annotations.ClientData

@ClientData
data class GuildStateHouse(
	val guildHouseId: Int? // Might be -1
)
