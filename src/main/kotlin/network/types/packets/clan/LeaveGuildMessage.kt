package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when they want to leave a clan.
 *
 * Client to server.
 */
@InternalPacket(46)
class LeaveGuildMessage(
	msgType: Int,
) : NetworkMessage(msgType)