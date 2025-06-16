package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(13)
class EquipItemMessage(
	msgType: Int,

	val itemId: Int,
	val amount: Int,
) : NetworkMessage(msgType)