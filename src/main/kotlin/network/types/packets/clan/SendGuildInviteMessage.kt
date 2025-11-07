package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when we want to invite a player to our clan.
 *
 * Client to server.
 */
@InternalPacket(35)
class SendGuildInviteMessage(
	msgType: Int,

	val playerReceivingInvite: String
) : NetworkMessage(msgType)