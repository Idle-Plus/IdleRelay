package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the server when a clan member logs in.
 *
 * Server to client.
 */
@InternalPacket(42)
class GuildMemberLoggedInMessage(
	msgType: Int,

	val guildMemberName: String
) : NetworkMessage(msgType)