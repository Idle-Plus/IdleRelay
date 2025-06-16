package dev.uraxys.idleclient.tools.typescript

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import dev.uraxys.idleclient.tools.typescript.annotations.ClientData
import dev.uraxys.idleclient.tools.typescript.annotations.ClientDoc
import dev.uraxys.idleclient.tools.typescript.annotations.ClientIgnore
import dev.uraxys.idleclient.tools.typescript.annotations.ClientInclude
import dev.uraxys.idleclient.tools.typescript.annotations.ClientIncludeOnly
import dev.uraxys.idleclient.tools.typescript.annotations.ClientType
import io.github.classgraph.ClassGraph
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.time.Instant
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.superclasses

object TypeScriptGenerator {

	var enumKeysAsString: Boolean = false
	var upperCaseCamelCase: Boolean = false

	private val typeRegistry = mutableMapOf<KClass<*>, String>(
		Long::class to "Long /* i64 */",
		Int::class to "Int /* i32 */",
		Short::class to "Short /* i16 */",
		Byte::class to "Byte /* i8 */",

		Float::class to "Float /* f32 */",
		Double::class to "Double /* f64 */",

		String::class to "string",
		Boolean::class to "boolean",
		Instant::class to "string",

		IntArray::class to "Array<Int /* i32 */>",
		ShortArray::class to "Array<Short /* i16 */>",
		ByteArray::class to "Array<Byte /* i8 */>",
		FloatArray::class to "Array<Float /* f32 */>",
		DoubleArray::class to "Array<Double /* f64 */>",
		LongArray::class to "Array<Long /* i64 */>",
	)

	private var initialized = false
	fun initialize() {
		if (initialized) return
		initialized = true

		val reflections = ClassGraph().enableAllInfo().acceptPackages("dev.uraxys").scan()
		val classes = reflections.getClassesWithAnnotation(ClientData::class.java).loadClasses()

		for ((_, clazz) in classes.withIndex()) {
			val annotation = clazz.annotations.find { it is ClientData } as ClientData
			val typeName = if (annotation.type.isNotEmpty()) ClientData.parse(annotation.type) else clazz.simpleName

			val kClazz = clazz.kotlin
			typeRegistry[kClazz] = typeName
		}
	}

	fun generateInterface(clazz: KClass<*>): String {
		// Initialize the type registry.
		this.initialize()

		val builder = StringBuilder()
		var className = clazz.simpleName
		val includeOnly = hasAnnotation(clazz, ClientIncludeOnly::class)

		// If the class has "ClientData" annotation, then check if it has a custom name.
		if (hasAnnotation(clazz, ClientData::class)) {
			val annotation = getAnnotation(clazz, ClientData::class)
			if (annotation!!.type.isNotEmpty()) className = ClientData.parse(annotation.type)
		}

		builder.append("\n").append(generateDoc(clazz))
		builder.append("export interface $className {\n")

		val properties = clazz.memberProperties
		for (property in properties) {

			// If the class is annotated with IncludeOnly, then make sure the current
			// property has ClientInclude.
			if (includeOnly && !property.annotations.any { it.annotationClass == ClientInclude::class }) continue

			// Skip transient and TSIgnore annotations.
			if (property.annotations.any { it.annotationClass == Transient::class ||
						it.annotationClass == ClientIgnore::class }) continue

			// Get the custom type annotation, if we have any.
			var customType: ClientType? = getAnnotation(property, ClientType::class)
			if (customType == null) customType = getAnnotation(property.returnType, ClientType::class)

			// Get the documentation annotation, if we have any, then generate the documentation.
			val doc = getAnnotation(property, ClientDoc::class)
			if (doc != null) builder.append(generateDoc(doc.value, 1))

			// Set the type

			val name =
				if (upperCaseCamelCase) PropertyNamingStrategies.UpperCamelCaseStrategy.INSTANCE.translate(property.name)
				else property.name

			builder.append("\t${name}: ${customType?.value ?: getType(property)};\n")
		}

		builder.append("}\n")

		return builder.toString()
	}

	fun generateEnum(clazz: KClass<*>): String {
		// Initialize the type registry.
		this.initialize()

		val builder = StringBuilder()
		var className = clazz.simpleName

		// If the class has "ClientData" annotation, then check if it has a custom name.
		if (hasAnnotation(clazz, ClientData::class)) {
			val annotation = getAnnotation(clazz, ClientData::class)
			if (annotation!!.type.isNotEmpty()) className = ClientData.parse(annotation.type)
		}

		builder.append("\n").append(generateDoc(clazz))
		builder.append("export enum $className {\n")

		val values = clazz.java.enumConstants
		for (value in values) {
			builder.append("\t").append(value).append(",\n")
		}

		builder.append("}\n")

		return builder.toString()
	}

	fun generateDoc(clazz: KClass<*>, tabs: Int = 0): String {
		// Initialize the type registry.
		this.initialize()

		if (!hasAnnotation(clazz, ClientDoc::class)) return ""
		val annotation = getAnnotation(clazz, ClientDoc::class)
		val builder = StringBuilder()
		val tab = "\t".repeat(tabs)

		builder.append(tab).append("/**\n")
		var currentLine = ""
		for (line in annotation!!.value.lines()) {
			if (currentLine.isNotEmpty()) {
				builder.append(tab).append(" * $currentLine\n")
				currentLine = ""
			}

			for (word in line.split(" ")) {
				if (currentLine.length + word.length > 80) {
					builder.append(tab).append(" * $currentLine\n")
					currentLine = ""
				}
				currentLine += "$word "
			}
		}
		// If we have a current line, append it.
		if (currentLine.isNotEmpty()) builder.append(tab).append(" * $currentLine\n")
		builder.append(tab).append(" */\n")

		return builder.toString()
	}

	fun generateDoc(doc: String, tabs: Int = 0): String {
		// Initialize the type registry.
		this.initialize()

		val builder = StringBuilder()
		val tab = "\t".repeat(tabs)

		builder.append(tab).append("/**\n")
		for (line in doc.lines()) builder.append(tab).append(" * $line\n")
		builder.append(tab).append(" */\n")

		return builder.toString()
	}

	/**
	 * Helpers
	 */

	// Type

	fun getType(parameter: KParameter, nullable: Boolean = false): String {
		// Initialize the type registry.
		this.initialize()

		val type: KType = parameter.type
		val optional = nullable || type.isMarkedNullable

		var customType: ClientType? = getAnnotation(parameter, ClientType::class)
		if (customType != null) return customType.value + if (optional) " | null" else ""

		customType = getAnnotation(type, ClientType::class)
		if (customType != null) return customType.value + if (optional) " | null" else ""

		if (type.classifier !is KClass<*>) {
			println("WARNING: ${parameter.name} is not a KClass. ${parameter.type}")
			return "any${if (optional) " | null" else ""}"
		}
		val clazz: KClass<*> = type.classifier as KClass<*>

		if (clazz.isSubclassOf(Map::class)) {
			val keyType = type.arguments[0].type!!
			val valueType = type.arguments[1].type!!

			val realKey = getType(keyType)
			val key = if (enumKeysAsString && (keyType.classifier as? KClass<*>)?.java?.isEnum == true)
				"string /* Name of: $realKey */" else realKey

			return "Map<${key}, ${getType(valueType)}>${if (optional) " | null" else ""}"
		}

		if (clazz.isSubclassOf(List::class)) {
			val valueType = type.arguments[0].type!!
			return "Array<${getType(valueType)}>${if (optional) " | null" else ""}"
		}

		if (clazz.isSubclassOf(Array::class)) {
			val valueType = type.arguments[0].type!!
			return "Array<${getType(valueType)}>${if (optional) " | null" else ""}"
		}

		if (clazz.java.isArray) {
			val componentType = clazz.java.componentType
			return "Array<${getType(componentType.kotlin)}>${if (optional) " | null" else ""}"
		}

		return getType(clazz, optional)
	}

	fun getType(property: KProperty<*>, nullable: Boolean = false): String {
		// Initialize the type registry.
		this.initialize()

		val type: KType = property.returnType
		val optional = nullable || type.isMarkedNullable

		var customType: ClientType? = getAnnotation(property, ClientType::class)
		if (customType != null) return customType.value + if (optional) " | null" else ""

		customType = getAnnotation(type, ClientType::class)
		if (customType != null) return customType.value + if (optional) " | null" else ""

		if (type.classifier !is KClass<*>) {
			println("WARNING: ${property.name} is not a KClass. ${property.returnType}")
			return "any${if (optional) " | null" else ""}"
		}
		val clazz: KClass<*> = type.classifier as KClass<*>

		if (typeRegistry.containsKey(clazz)) return typeRegistry[clazz]!! + if (optional) " | null" else ""
		// Check super classes in type registry.
		val superClassType = getSuperClassType(clazz)
		if (superClassType != null) return superClassType + if (optional) " | null" else ""

		if (clazz.isSubclassOf(Map::class)) {
			val keyType = type.arguments[0].type!!
			val valueType = type.arguments[1].type!!

			val realKey = getType(keyType)
			val key = if (enumKeysAsString && (keyType.classifier as? KClass<*>)?.java?.isEnum == true)
				"string /* Name of: $realKey */" else realKey

			return "Map<${key}, ${getType(valueType)}>${if (optional) " | null" else ""}"
		}

		if (clazz.isSubclassOf(List::class)) {
			val valueType = type.arguments[0].type!!
			return "Array<${getType(valueType)}>${if (optional) " | null" else ""}"
		}

		if (clazz.isSubclassOf(Array::class)) {
			val valueType = type.arguments[0].type!!
			return "Array<${getType(valueType)}>${if (optional) " | null" else ""}"
		}

		if (clazz.java.isArray) {
			val componentType = clazz.java.componentType
			return "Array<${getType(componentType.kotlin)}>${if (optional) " | null" else ""}"
		}

		return getType(clazz, optional)
	}

	fun getType(clazz: KClass<*>, nullable: Boolean = false): String {
		// Initialize the type registry.
		this.initialize()

		if (typeRegistry.containsKey(clazz)) return typeRegistry[clazz]!! + if (nullable) " | null" else ""
		// Check super classes in type registry.
		val superClassType = getSuperClassType(clazz)
		if (superClassType != null) return superClassType + if (nullable) " | null" else ""

		if (clazz.isSubclassOf(Map::class)) {
			val type = clazz.java.genericSuperclass
			if (type is ParameterizedType) {
				val keyType = type.actualTypeArguments[0]
				val valueType = type.actualTypeArguments[1]

				val realKey = getType(keyType)
				val key = if (enumKeysAsString && keyType.javaClass.isEnum)
					"string /* Name of: $realKey */" else realKey

				return "Map<${key}, ${getType(valueType)}>${if (nullable) " | null" else ""}"
			}
		}

		if (clazz.isSubclassOf(List::class)) {
			val type = clazz.java.genericSuperclass
			if (type is ParameterizedType) {
				val valueType = type.actualTypeArguments[0]
				return "Array<${getType(valueType)}>${if (nullable) " | null" else ""}"
			}
		}

		if (clazz.isSubclassOf(Array::class)) {
			val type = clazz.java.genericSuperclass
			if (type is ParameterizedType) {
				val valueType = type.actualTypeArguments[0]
				return "Array<${getType(valueType)}>${if (nullable) " | null" else ""}"
			}
		}

		println("WARNING: ${clazz.simpleName} is not registered in the type registry, defaulting to 'any'.")
		return "any${if (nullable) " | null" else ""}"
	}

	fun getType(type: KType, nullable: Boolean = false): String {
		// Initialize the type registry.
		this.initialize()

		val optional = nullable || type.isMarkedNullable

		if (type.classifier !is KClass<*>) {
			println("WARNING: ${type.classifier} is not a KClass. $type")
			return "any${if (optional) " | null" else ""}"
		}
		val clazz: KClass<*> = type.classifier as KClass<*>
		return getType(clazz, optional)
	}

	fun getType(type: Type, nullable: Boolean = false): String {
		return when (type) {
			is Class<*> -> getType(type.kotlin, nullable)
			else -> {
				println("WARNING: Unknown type: $type, defaulting to 'any'")
				"any${if (nullable) " | null" else ""}"
			}
		}
	}

	// Super class to type

	private fun getSuperClassType(clazz: KClass<*>): String? {
		// Initialize the type registry.
		this.initialize()

		for (superClass in clazz.superclasses) {
			if (typeRegistry.containsKey(superClass)) return typeRegistry[superClass]!!
			val type = getSuperClassType(superClass)
			if (type != null) return type
		}
		return null
	}

	// Annotations

	private fun <T : Annotation> hasAnnotation(element: KAnnotatedElement, annotation: KClass<out T>): Boolean {
		return element.annotations.any { it.annotationClass == annotation }
	}

	private fun <T : Annotation> getAnnotation(element: KAnnotatedElement, annotation: KClass<out T>): T? {
		@Suppress("UNCHECKED_CAST")
		return element.annotations.find { it.annotationClass == annotation } as T?
	}
}