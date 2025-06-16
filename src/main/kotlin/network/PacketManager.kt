package dev.uraxys.idleclient.network

import com.fasterxml.jackson.databind.node.ObjectNode
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import dev.uraxys.idleclient.utils.utils.Common
import dev.uraxys.idleclient.utils.utils.LogUtils
import io.github.classgraph.ClassGraph
import kotlin.collections.set
import kotlin.reflect.KClass

object PacketManager {

	private val log = LogUtils.create(PacketManager::class)

	private val PACKETS_BY_ID = mutableMapOf<Int, KClass<out NetworkMessage>>()
	private val PACKETS_BY_CLASS = mutableMapOf<KClass<out NetworkMessage>, Int>()

	fun initialize() {
		PACKETS_BY_ID.clear()
		PACKETS_BY_CLASS.clear()

		val reflections = ClassGraph()
			.enableAllInfo()
			.acceptPackages("dev.uraxys")
			.scan()

		val packetClasses = reflections.getClassesWithAnnotation(InternalPacket::class.java)
			.loadClasses().sortedBy { it.simpleName }

		for (clazz in packetClasses) {
			if (!NetworkMessage::class.java.isAssignableFrom(clazz))
				throw IllegalArgumentException("Packet class $clazz does not extend NetworkMessage")

			@Suppress("UNCHECKED_CAST")
			val klass = clazz.kotlin as KClass<out NetworkMessage>
			val annotation = klass.annotations.find { it is InternalPacket } as InternalPacket
			val id = annotation.value

			this.PACKETS_BY_ID[id] = klass
			this.PACKETS_BY_CLASS[klass] = id
		}
	}

	fun decode(json: ObjectNode): NetworkMessage? {
		val id = json["MsgType"]?.asInt() ?: return null
		val packetClass = PACKETS_BY_ID[id] ?: return null
		// Will throw an exception if the packet is missing values or has
		// invalid data types. Which is what we want, as we want to know
		// if a packet we're handling is suddenly not working anymore.
		return Common.RELAY_INBOUND_NETWORK_JSON.treeToValue(json, packetClass.java)
	}
}