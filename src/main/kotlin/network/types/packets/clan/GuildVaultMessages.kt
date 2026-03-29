package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(GamePacket.RequestGuildVaultMessage)
class RequestGuildVaultMessage(
	msgType: Int
) : NetworkMessage(msgType)

@InternalPacket(GamePacket.GuildVaultMessage)
class GuildVaultMessage(
	msgType: Int,

	val vault: Map<Int, Int>
) : NetworkMessage(msgType)