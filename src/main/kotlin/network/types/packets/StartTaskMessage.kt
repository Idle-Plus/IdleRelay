package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("Start a task, client to server only.")
@InternalPacket(GamePacket.StartTaskMessage)
class StartTaskMessage(
	msgType: Int,

	val taskType: TaskType,
	val taskId: Int,
	val useInventoryConsumables: Boolean,
) : NetworkMessage(msgType)