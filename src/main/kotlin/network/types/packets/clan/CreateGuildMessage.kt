package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.data.guild.DailyGuildQuest
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import java.time.Instant

/**
 * Sent from the client when creating a clan.
 *
 * When sent from the client, only the guild name should be specified. This
 * packet was previously used by the server when the clan was created, but
 * that is no longer the case.
 *
 * Client to server.
 */
@InternalPacket(GamePacket.CreateGuildMessage)
class CreateGuildMessage(
	msgType: Int,

	val guildName: String,
	val nextQuestGenerationTimestamp: Instant?, // Doesn't seem to be used anymore.
	val dailySkillingQuests: List<DailyGuildQuest>?, // Doesn't seem to be used anymore.
	val dailyCombatQuests: List<DailyGuildQuest>?, // Doesn't seem to be used anymore.
) : NetworkMessage(msgType)