package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.enums.GuildActionResponse
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by the server as a response to a different request.
 *
 * Server to client.
 */
@InternalPacket(41)
class GuildRequestResultMessage(
	msgType: Int,

	val associatedPlayer: String?,
	val messageType: GuildActionResponse
) : NetworkMessage(msgType)