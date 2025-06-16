package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(14)
class UnequipItemMessage(
	msgType: Int,

	val itemId: Int,
) : NetworkMessage(msgType)