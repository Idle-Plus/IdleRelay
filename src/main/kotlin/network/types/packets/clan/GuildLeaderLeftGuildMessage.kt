package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the server when the clan leader leaves the clan, is also sent to
 * the player that left.
 *
 * Server to client.
 */
@InternalPacket(124)
class GuildLeaderLeftGuildMessage(
	msgType: Int,

	val newLeader: String
) : NetworkMessage(msgType)