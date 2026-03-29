package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.enums.ClanCategory
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by both the client and the server when the clan's recruitment state
 * has been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildUpdateRecruitmentStateMessage)
class GuildUpdateRecruitmentStateMessage(
	msgType: Int,

	val value: Boolean,
) : NetworkMessage(msgType)

/**
 * Sent by both the client and the server when the clan category has been
 * changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildUpdateStatusMessage)
class GuildUpdateStatusMessage(
	msgType: Int,

	val status: ClanCategory
) : NetworkMessage(msgType)

/**
 * Sent by both the client and the server when the clan's primary language has
 * been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildUpdatePrimaryLanguageMessage)
class GuildUpdatePrimaryLanguageMessage(
	msgType: Int,

	val language: String
) : NetworkMessage(msgType)

/**
 * Sent by both the client and the server when the clan's recruitment message
 * has been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildUpdateRecruitmentMessageMessage)
class GuildUpdateRecruitmentMessageMessage(
	msgType: Int,

	val recruitmentMessage: String
) : NetworkMessage(msgType)

/**
 * Sent by both the client and the server when the clan's minimum total level
 * requirement has been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildUpdateMinimumTotalLevelRequirementMessage)
class GuildUpdateMinimumTotalLevelRequirementMessage(
	msgType: Int,

	val minimumTotalLevel: Int
) : NetworkMessage(msgType)

/**
 * Sent by the client when the player wants to request the current recruitment
 * message of the clan. Server then sends back the message using the same packet.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildRequestRecruitmentMessageMessage)
class GuildRequestRecruitmentMessageMessage(
	msgType: Int,

	val recruitmentMessage: String?
) : NetworkMessage(msgType)

/**
 * Sent by both the client and the server when the clan's tag has been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.GuildUpdateTagMessage)
class GuildUpdateTagMessage(
	msgType: Int,

	val tag: String?
) : NetworkMessage(msgType)