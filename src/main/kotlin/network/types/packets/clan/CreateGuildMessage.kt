package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.data.DailyGuildQuest
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import java.time.Instant

/**
 * Sent from both the client and the server when creating a clan.
 *
 * When sent from the client, only the guild name should be specified. While
 * all fields should be specified when received from the server.
 *
 * Client to server and server to client.
 */
@InternalPacket(32)
class CreateGuildMessage(
	msgType: Int,

	val guildName: String,
	val nextQuestGenerationTimestamp: Instant?,
	val dailySkillingQuests: List<DailyGuildQuest>?,
	val dailyCombatQuests: List<DailyGuildQuest>?,
) : NetworkMessage(msgType)