package dev.uraxys.idleclient.network.types.packets.item

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when selling an item, the server responds with the same
 * packet, confirming the action and what items changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.SellItemMessage)
class SellItemMessage(
	msgType: Int,

	val itemId: Int,
	val itemAmount: Int,

	val itemsToAdd: Map<Int, Int>?,
	val ItemsToRemove: Map<Int, Int>?
) : NetworkMessage(msgType)