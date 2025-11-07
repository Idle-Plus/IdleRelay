package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the server when a player leaves a clan, is also sent to the player
 * that left the clan.
 *
 * Server to client.
 */
@InternalPacket(48)
class PlayerLeftGuildMessage(
	msgType: Int,

	val playerName: String
) : NetworkMessage(msgType)