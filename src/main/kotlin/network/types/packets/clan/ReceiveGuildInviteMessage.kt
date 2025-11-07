package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the server when receiving an invitation to join a clan.
 *
 * Server to client.
 */
@InternalPacket(36)
class ReceiveGuildInviteMessage(
	msgType: Int,

	val playerInviting: String,
	val guildName: String
) : NetworkMessage(msgType)