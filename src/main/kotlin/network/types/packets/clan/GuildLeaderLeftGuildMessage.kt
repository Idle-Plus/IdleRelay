package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by the server to a guild leader when they left the clan. Only the leaving
 * guild leader will receive the message, no one else.
 *
 * A guild state message will be sent to everyone else in the clan, updating them
 * of the new leader.
 *
 * Server to client.
 */
@ClientDoc("Sent by the server to a guild leader when they left the clan. Only " +
		"the leaving guild leader will receive the message, no one else.")
@InternalPacket(GamePacket.GuildLeaderLeftGuildMessage)
class GuildLeaderLeftGuildMessage(
	msgType: Int,

	val newLeader: String
) : NetworkMessage(msgType)