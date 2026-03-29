package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.data.player.PlayerActivity
import dev.uraxys.idleclient.network.types.enums.PvmStatType
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/**
 * Set from both the client and the server when requesting the clan's PvM stats
 * and member activities.
 *
 * Client to server and server to client.
 */
@InternalPacket(GamePacket.RequestGuildPageInfoMessage)
class RequestGuildPageInfoMessage(
	msgType: Int,

	val pvmStats: Map<PvmStatType, Int>?,
	val clanMemberActivities: Map<String, PlayerActivity>?,
) : NetworkMessage(msgType)