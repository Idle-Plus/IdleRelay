package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(69)
class InventoryItemSwapMessage(
	msgType: Int,

	val fromSlot: Int,
	val toSlot: Int
) : NetworkMessage(msgType)