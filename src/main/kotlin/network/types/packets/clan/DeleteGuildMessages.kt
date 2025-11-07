package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import java.time.Instant

/**
 * Sent by the client when we want to delete our clan. The server will either
 * delete our clan instantly and respond with [GuildDeletedMessage], or it'll
 * respond with [GuildDeletionResponseMessage] which contains the earliest
 * possible deletion data.
 *
 * If [GuildDeletionResponseMessage] is received, we will need to wait until
 * the time specified in the packet before being able to delete our clan. We
 * can also abort the deletion process by sending [AbortGuildDeletionMessage].
 *
 * viewingDeletionState - No idea what this is for at the moment, but it's
 * false when trying to delete a clan. Maybe it should be true when we're
 * viewing the deletion screen (where it's possible to abort the deletion).
 *
 * Client to server.
 */
@InternalPacket(47)
class DeleteGuildMessage(
	msgType: Int,

	val viewingDeletionState: Boolean
) : NetworkMessage(msgType)

/**
 * Sent from the server when the deletion process has been started, but still
 * requires us to wait until the specified time before deleting our clan.
 *
 * Server to client.
 */
@InternalPacket(265)
class GuildDeletionResponseMessage(
	msgType: Int,

	val earliestPossibleDeletionDate: Instant
) : NetworkMessage(msgType)

/**
 * Most likely sent from the client when we want to abort the deletion process.
 * Might also be sent from the server when the deletion process has been aborted.
 *
 * // NOT SURE
 */
@InternalPacket(266)
class AbortGuildDeletionMessage(
	msgType: Int
) : NetworkMessage(msgType)

/**
 * Sent from the server when our clan has been deleted.
 *
 * Server to client.
 */
@InternalPacket(49)
class GuildDeletedMessage(
	msgType: Int
) : NetworkMessage(msgType)