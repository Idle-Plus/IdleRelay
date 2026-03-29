package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(GamePacket.EquipItemMessage)
class EquipItemMessage(
	msgType: Int,

	val itemId: Int,
	val amount: Int,
) : NetworkMessage(msgType)