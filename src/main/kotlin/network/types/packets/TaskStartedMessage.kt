package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("Confirmation that the task has been started, server to client only.")
@InternalPacket(191)
class TaskStartedMessage(
	msgType: Int,

	val taskType: TaskType,
	val taskId: Int,
) : NetworkMessage(msgType)