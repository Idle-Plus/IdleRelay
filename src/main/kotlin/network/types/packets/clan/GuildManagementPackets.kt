package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.enums.ClanCategory
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Sent by both the client and the server when the clan's recruitment state
 * has been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(212)
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
@InternalPacket(213)
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
@InternalPacket(214)
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
@InternalPacket(215)
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
@InternalPacket(216)
class GuildUpdateMinimumTotalLevelRequirementMessage(
	msgType: Int,

	val minimumTotalLevel: Int
) : NetworkMessage(msgType)

/**
 * Sent by both the client and the server when the clan's tag has been changed.
 *
 * Client to server and server to client.
 */
@InternalPacket(354)
class GuildUpdateTagMessage(
	msgType: Int,

	val tag: String?
) : NetworkMessage(msgType)