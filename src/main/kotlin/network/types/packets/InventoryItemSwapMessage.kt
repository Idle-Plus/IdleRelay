package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(GamePacket.InventoryItemSwapMessage)
class InventoryItemSwapMessage(
	msgType: Int,

	val fromSlot: Int,
	val toSlot: Int
) : NetworkMessage(msgType)