package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

// Client to server
@InternalPacket(188)
class RequestGuildBulletinInfoMessage(
	msgType: Int,
) : NetworkMessage(msgType)

// Server to client: response to RequestGuildBulletinInfoMessage
// Client to server: edit bulletin board
@InternalPacket(189)
class GuildBulletinBoardInfoMessage(
	msgType: Int,

	val message: String?,
	val discordInvitationCode: String?
) : NetworkMessage(msgType)

// Server to client: response to GuildBulletinBoardInfoMessage
@InternalPacket(190)
class GuildBulletinBoardEditResponseMessage(
	msgType: Int,

	val success: Boolean
) : NetworkMessage(msgType)