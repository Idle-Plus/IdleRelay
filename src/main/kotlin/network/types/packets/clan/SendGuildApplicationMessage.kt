package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by the client when a player sends a clan application to a specific clan,
 * any online clan members will receive [ReceiveGuildApplicationMessage] from the
 * server.
 *
 * Client to server.
 */
@InternalPacket(33)
class SendGuildApplicationMessage(
	msgType: Int,

	val guildName: String,
	val message: String
) : NetworkMessage(msgType)