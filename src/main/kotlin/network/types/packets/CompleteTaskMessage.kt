package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("A task has been completed, server to client only.")
@InternalPacket(4)
class CompleteTaskMessage(
	msgType: Int,

	val taskType: TaskType,
	val taskId: Byte,
	val itemAmount: Int,
	val upgradeInteraction: Byte,
	val potionInteraction: Byte,
	val usedConsumableItemId: Int
) : NetworkMessage(msgType)