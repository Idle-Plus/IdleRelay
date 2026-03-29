package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by the server to a player that left or was kicked from the clan. Only the
 * player being removed will receive the message, no one else.
 *
 * A guild state message will be sent to everyone else in the clan, updating them
 * about the new clan state.
 *
 * Server to client.
 */
@ClientDoc("Sent by the server to a player that left or was kicked from the clan. " +
		"Only the player being removed will receive the message, no one else.")
@InternalPacket(GamePacket.PlayerLeftGuildMessage)
class PlayerLeftGuildMessage(
	msgType: Int,

	val playerName: String
) : NetworkMessage(msgType)