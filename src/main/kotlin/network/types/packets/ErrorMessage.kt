package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.enums.ErrorType
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(5)
class ErrorMessage(
	msgType: Int,

	val error: ErrorType,
	val locKey: String?
) : NetworkMessage(msgType)