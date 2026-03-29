package dev.uraxys.idleclient.network.types.packets

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.data.item.ItemDelta
import dev.uraxys.idleclient.network.types.data.skill.ExpDelta
import dev.uraxys.idleclient.network.types.enums.TaskType
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("A task has been completed, server to client only.")
@InternalPacket(GamePacket.CompleteTaskMessage)
class CompleteTaskMessage(
	msgType: Int,

	val taskType: TaskType,
	val taskId: Byte,
	val itemChanges: Array<ItemDelta>,
	val expChanges: Array<ExpDelta>,
) : NetworkMessage(msgType)