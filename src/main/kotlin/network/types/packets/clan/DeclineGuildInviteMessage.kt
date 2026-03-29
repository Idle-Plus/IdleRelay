package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when we want to decline a clan invitation.
 *
 * Client to server.
 */
@InternalPacket(GamePacket.DeclineGuildInviteMessage)
class DeclineGuildInviteMessage(
	msgType: Int,

	val guildName: String
) : NetworkMessage(msgType)