package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.enums.ClanCategory
import dev.uraxys.idleclient.network.types.enums.Skill
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(59)
class RequestGuildStateMessage(
	msgType: Int,
) : NetworkMessage(msgType)

@InternalPacket(60)
class ReceiveGuildStateMessage(
	msgType: Int,

	val skillExperiences: Map<Skill, Float>,
	val clanCredits: Int,
	val eventStates: String,
	val skillingTickets: Map<Skill, Int>,

	val skillingPartyCompletions: Int,
	val isRecruiting: Boolean,
	val status: ClanCategory = ClanCategory.None,
	val primaryLanguage: String?,
	val minimumTotalLevelRequired: Int,
	val localPlayerHasClaimableLoot: Boolean,
	val tag: String?
) : NetworkMessage(msgType)