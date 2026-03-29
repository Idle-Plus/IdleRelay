package dev.uraxys.idleclient.network.types.packets.clan

import dev.uraxys.idleclient.network.types.GamePacket
import dev.uraxys.idleclient.network.types.data.guild.GuildStateAdmin
import dev.uraxys.idleclient.network.types.data.guild.GuildStateChangeMessage
import dev.uraxys.idleclient.network.types.data.guild.GuildStateEconomy
import dev.uraxys.idleclient.network.types.data.guild.GuildStateEvents
import dev.uraxys.idleclient.network.types.data.guild.GuildStateHouse
import dev.uraxys.idleclient.network.types.data.guild.GuildStateMembers
import dev.uraxys.idleclient.network.types.data.guild.GuildStateMeta
import dev.uraxys.idleclient.network.types.data.guild.GuildStateProgress
import dev.uraxys.idleclient.network.types.data.guild.GuildStateQuests
import dev.uraxys.idleclient.network.types.data.guild.GuildStateVault
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket

@InternalPacket(GamePacket.ReceiveGuildStateMessage)
class ReceiveGuildStateMessage(
	//msgType: Int,
	val isPartialUpdate: Boolean,
	val meta: GuildStateMeta?,
	val progress: GuildStateProgress?,
	val members: GuildStateMembers?,
	val vault: GuildStateVault?,
	val house: GuildStateHouse?,
	val economy: GuildStateEconomy?,
	val quests: GuildStateQuests?,
	val events: GuildStateEvents?,
	val message: GuildStateChangeMessage?, // Original object named StateChangeMessage
	val admin: GuildStateAdmin?,
) : NetworkMessage()