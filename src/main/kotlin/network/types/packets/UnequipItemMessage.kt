package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(GamePacket.UnequipItemMessage)
class UnequipItemMessage(
	msgType: Int,

	val itemId: Int,
) : NetworkMessage(msgType)