package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by the server when receiving a clan application.
 *
 * Server to client.
 */
@InternalPacket(34)
class ReceiveGuildApplicationMessage(
	msgType: Int,

	val playerApplying: String,
	val message: String,
	val playerTotalLevel: Int
) : NetworkMessage(msgType)