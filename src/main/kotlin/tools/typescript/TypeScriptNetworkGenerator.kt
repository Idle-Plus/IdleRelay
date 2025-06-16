package dev.uraxys.idleclient.tools.typescript

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import dev.uraxys.idleclient.network.types.packets.NetworkMessage
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.ClientIgnore
import dev.uraxys.idleclient.tools.typescript.annotations.InternalPacket
import dev.uraxys.idleclient.utils.extensions.has
import io.github.classgraph.ClassGraph
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

private const val PACKAGE = "dev.uraxys"

private const val HEADER = """
// Automatically generated file, do not edit manually.

export abstract class Packet { abstract readonly MsgType: number; }

export type Long = number;
export type Int = number;
export type Short = number;
export type Byte = number;
export type Float = number;
export type Double = number;
"""

private const val FOOTER = """
export function deserialize(json: any): Packet | null {
	try {
		const packetType = PacketRegistry[json.MsgType];
		if (!packetType) return null;

		return packetType.fromJson(json);
	} catch (e) {
		console.error("Failed to deserialize packet:", e);
		return null;
	}
}
"""

private fun main() {

	TypeScriptGenerator.enumKeysAsString = true
	TypeScriptGenerator.upperCaseCamelCase = true

	val file = StringBuilder()
	file.append(HEADER)

	// Fill all the packets.
	val scan = ClassGraph().enableAllInfo().acceptPackages(PACKAGE).scan()
	val packetsClasses = scan.getSubclasses(NetworkMessage::class.java).loadClasses()
	val packets = packetsClasses
		.filter { it.kotlin.hasAnnotation<InternalPacket>() }
		.map { it.kotlin as KClass<out NetworkMessage> }
		.associateBy { it.annotations.filterIsInstance<InternalPacket>().first().value }

	val packetRegistry = StringBuilder()
	packetRegistry.append("const PacketRegistry: any = {\n")

	val packetTypes = StringBuilder()
	packetTypes.append("export enum PacketType {\n")

	generateCommentTS(file, "Data Types", spaceStart = true, spaceEnd = false)
	generateData(file)
	generatePackets(file, packetRegistry, packetTypes, packets)

	generateCommentTS(file, "Packet Registry\nMapping packet IDs to their respective classes.", spaceEnd = true)
	packetRegistry.append("};\n\n")
	file.append(packetRegistry)

	generateCommentTS(file, "Packet Type\nEnum with all the available packets the client can send.", spaceEnd = true)
	packetTypes.append("}\n")
	file.append(packetTypes)
	file.append(FOOTER)

	println(file)
}

private fun generateData(file: StringBuilder) {

	val reflections = ClassGraph().enableAllInfo().acceptPackages(PACKAGE).scan()
	val classes = reflections.getClassesWithAnnotation(ClientData::class.java).loadClasses()
	val dataRegistry = mutableSetOf<KClass<*>>()

	for ((_, clazz) in classes.withIndex()) {
		val kClazz = clazz.kotlin
		dataRegistry.add(kClazz)
	}

	for (data in dataRegistry) {
		if (data.java.isEnum) file.append(TypeScriptGenerator.generateEnum(data))
		else file.append(TypeScriptGenerator.generateInterface(data))
	}

	file.appendLine()
}

private fun generatePackets(file: StringBuilder, packetRegistry: StringBuilder, packetTypes: StringBuilder,
                            packets: Map<Int, KClass<out NetworkMessage>>) {
	for (entry in packets) {
		val packet = entry.value
		val packetName = packet.simpleName
		val packetId = entry.key

		generateClientDocTS(packet, file)

		val parent = "Packet"
		val modifier = ""

		file.append("export class $packetName extends $parent {\n")
		file.append("\tpublic readonly MsgType: number = $packetId;\n")
		file.append("\t${modifier}constructor(")

		val constructor = packet.constructors.first { !it.has(ClientIgnore::class) }
		val parameters = constructor.parameters

		for (parameter in parameters) {
			val name = PropertyNamingStrategies.UpperCamelCaseStrategy.INSTANCE.translate(parameter.name)
			//val nullable = parameter.type.isMarkedNullable

			if (name == "MsgType") continue

			//if (nullable) file.append("public $name: ")
			//else file.append("public $name: ")
			file.append("public $name: ")
			file.append(TypeScriptGenerator.getType(parameter)).append(", ")
		}

		// Remove the last comma and space.
		if (parameters.isNotEmpty() && parameters.size > 1) file.setLength(file.length - 2)
		file.append(") { super(); }\n\n")

		// Static method to serialize the packet.
		file.append("\tpublic static fromJson(${if (parameters.isNotEmpty()) "json" else "_"}: any): $packetName {\n")
		file.append("\t\treturn new $packetName(")
		for (parameter in parameters) {
			val name = PropertyNamingStrategies.UpperCamelCaseStrategy.INSTANCE.translate(parameter.name)
			if (name == "MsgType") continue
			file.append("json.$name, ")
		}
		if (parameters.isNotEmpty() && parameters.size > 1) file.setLength(file.length - 2)
		file.append(");\n")
		file.append("\t}\n")

		// End of class.
		file.append("}\n\n")

		// Packet registry.
		packetRegistry.append("\t$packetId: $packetName,\n")
		// Packet type enum.
		packetTypes.append("\t$packetName = $packetId,\n")
	}
}

private fun generateClientDocTS(clazz: KClass<*>, file: StringBuilder) {
	val docAnnotation = clazz.annotations.find { it.annotationClass == ClientDoc::class }
	if (docAnnotation != null) {
		val docValue = (docAnnotation as ClientDoc).value
		val lines = mutableListOf<String>()

		// Automatically split after 80 characters, split on spaces.
		var currentLine = ""
		for (word in docValue.split(" ")) {
			if (currentLine.length + word.length > 80) {
				lines.add(currentLine)
				currentLine = ""
			}
			currentLine += "$word "
		}
		lines.add(currentLine)

		file.append("/**\n")
		for (line in lines) file.append(" * $line\n")
		file.append(" */\n")
	}
}

private fun generateCommentTS(file: StringBuilder, comment: String, doc: Boolean = false,
                              spaceStart: Boolean = false, spaceEnd: Boolean = true) {
	val lines = comment.split("\n")

	if (spaceStart) file.append("\n")

	if (doc) file.append("/**\n")
	else file.append("/*\n")
	for (line in lines) file.append(" * $line\n")
	file.append(" */\n")

	if (spaceEnd) file.append("\n")
}