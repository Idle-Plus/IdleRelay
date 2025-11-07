package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent from the client when all clan applications should be cleared. Then
 * sent back to all online members from the server to confirm the applications
 * have been cleared.
 *
 * Client to server and server to client.
 */
@InternalPacket(132)
class ClearAllGuildApplicationsMessage(
	msgType: Int
) : NetworkMessage(msgType)