package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.enums.ClanBossModifierType
import dev.uraxys.idleclient.network.types.enums.ClanEventBossType
import dev.uraxys.idleclient.network.types.enums.PvmStatType
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@ClientDoc("<->")
@InternalPacket(337)
class RequestClanBossInfoMessage(
	msgType: Int,

	val bossType: ClanEventBossType,
	val stats: Map<PvmStatType, Int>?,
	val playersInFight: Byte?,
	val activeModifiers: Map<ClanBossModifierType, Int>?
) : NetworkMessage(msgType)