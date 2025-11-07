package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(218)
class RequestGuildVaultMessage(
	msgType: Int
) : NetworkMessage(msgType)

@InternalPacket(219)
class GuildVaultMessage(
	msgType: Int,

	val vault: Map<Int, Int>
) : NetworkMessage(msgType)