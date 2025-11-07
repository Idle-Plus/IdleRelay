package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when we want to kick a player from our clan.
 *
 * Client to server.
 */
@InternalPacket(50)
class KickGuildMemberMessage(
	msgType: Int,

	val username: String
) : NetworkMessage(msgType)

/**
 * Sent from the server when a player is kicked from the clan, is also sent to
 * the player that was kicked.
 *
 * Server to client.
 */
@InternalPacket(51)
class GuildMemberKickedMessage(
	msgType: Int,

	val playerName: String
) : NetworkMessage(msgType)