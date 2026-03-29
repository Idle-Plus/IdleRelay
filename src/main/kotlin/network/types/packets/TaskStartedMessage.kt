package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("Confirmation that the task has been started, server to client only.")
@InternalPacket(GamePacket.TaskStartedMessage)
class TaskStartedMessage(
	msgType: Int,

	val taskType: TaskType,
	val taskId: Int,
	val elapsedMs: Int,
) : NetworkMessage(msgType)