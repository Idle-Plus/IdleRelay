package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by the client to request the bulletin board info from the server, the
 * server responds with [GuildBulletinBoardInfoMessage].
 *
 * Client to server
 */
@InternalPacket(GamePacket.RequestGuildBulletinInfoMessage)
class RequestGuildBulletinInfoMessage(
	msgType: Int,
) : NetworkMessage(msgType)

/**
 * Sent by the server as a response to [RequestGuildBulletinInfoMessage]. Is
 * also sent by the client when editing the bulletin board.
 *
 * Server to client and client to server
 */
@InternalPacket(GamePacket.GuildBulletinBoardInfoMessage)
class GuildBulletinBoardInfoMessage(
	msgType: Int,

	val message: String?,
	val discordInvitationCode: String?
) : NetworkMessage(msgType)

/**
 * Sent by the server as a response to [GuildBulletinBoardInfoMessage] from the
 * client trying to edit the bulletin board.
 *
 * Client to server
 */
@InternalPacket(GamePacket.GuildBulletinBoardEditResponseMessage)
class GuildBulletinBoardEditResponseMessage(
	msgType: Int,

	val success: Boolean
) : NetworkMessage(msgType)