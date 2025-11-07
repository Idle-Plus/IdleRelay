package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when we want to accept a clan invitation.
 *
 * Client to server.
 */
@InternalPacket(37)
class AcceptGuildInviteMessage(
	msgType: Int,

	val guildName: String
) : NetworkMessage(msgType)