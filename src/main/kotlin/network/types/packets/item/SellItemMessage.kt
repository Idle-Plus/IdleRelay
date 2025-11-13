package dev.uraxys.idleclient.network.types.packets.item

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when selling an item, the server responds with the same
 * packet, confirming the action.
 *
 * Client to server and server to client.
 */
@InternalPacket(6)
class SellItemMessage(
	msgType: Int,

	val itemId: Int,
	val itemAmount: Int
) : NetworkMessage(msgType)