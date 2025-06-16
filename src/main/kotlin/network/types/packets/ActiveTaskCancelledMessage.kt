package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("Confirmation that the active task has been cancelled, server to client only.")
@InternalPacket(192)
class ActiveTaskCancelledMessage(
	msgType: Int,
) : NetworkMessage(msgType)