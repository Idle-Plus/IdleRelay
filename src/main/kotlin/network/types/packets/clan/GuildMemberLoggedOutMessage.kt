package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the server when a clan member logs out.
 *
 * Server to client.
 */
@InternalPacket(GamePacket.GuildMemberLoggedOutMessage)
class GuildMemberLoggedOutMessage(
	msgType: Int,

	val guildMemberName: String
) : NetworkMessage(msgType)