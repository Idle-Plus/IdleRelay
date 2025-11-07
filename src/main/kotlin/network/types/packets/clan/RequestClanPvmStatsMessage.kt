package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.enums.PvmStatType
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

/*
 * Original class name: RequestClanPvmStats
 * Changed to: RequestClanPvmStatsMessage
 */

/**
 * Sent from both the client and the server when requesting the clan's PvM
 * stats.
 *
 * Client to server and server to client.
 */
@InternalPacket(323)
class RequestClanPvmStatsMessage(
	msgType: Int,

	val stats: Map<PvmStatType, Int>?
) : NetworkMessage(msgType)