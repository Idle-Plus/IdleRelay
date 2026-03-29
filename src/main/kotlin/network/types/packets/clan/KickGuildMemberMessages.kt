package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when we want to kick a player from our clan.
 *
 * Client to server.
 */
@InternalPacket(GamePacket.KickGuildMemberMessage)
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
/*
@InternalPacket(GamePacket.GuildMemberKickedMessage)
class GuildMemberKickedMessage(
	msgType: Int,

	val playerName: String
) : NetworkMessage(msgType)*/
