package dev.uraxys.idleclient.utils.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object Common {

	/**
	 * Used when parsing messages from the server.
	 */
	val RELAY_INBOUND_NETWORK_JSON: ObjectMapper = ObjectMapper()
		.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
		.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT)
		.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		.registerKotlinModule()
		.registerModule(JavaTimeModule())
		.also { OmittedEnumDeserializer.registerModule(it) }

	/**
	 * Used when sending messages to the client.
	 */
	val RELAY_OUTBOUND_NETWORK_JSON: ObjectMapper = ObjectMapper()
		.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
		.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		.registerKotlinModule()
		.registerModule(JavaTimeModule())

	/**
	 * General use object mapper.
	 */
	val JSON: ObjectMapper = ObjectMapper()
		.registerKotlinModule()
		.registerModule(JavaTimeModule())
		.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

}

class OmittedEnumDeserializer<E : Enum<E>>(
	private val enumClass: Class<E>
) : StdDeserializer<E>(enumClass) {

	private val defaultValue = this.enumClass.enumConstants[0]
	private val valuesByName = this.enumClass.enumConstants.associateBy { it.name }

	override fun deserialize(p: JsonParser, ctxt: DeserializationContext): E {
		val idx = when {
			p.currentToken.isNumeric -> p.intValue
			p.currentToken.isScalarValue -> {
				val text = p.text
				return this.valuesByName[text]
					?: throw IllegalStateException("Unknown enum value $text for enum ${this.enumClass.canonicalName}.")
			}
			else -> throw IllegalStateException("Expected numeric value, got ${p.currentToken}")
		}

		return this.enumClass.enumConstants.getOrElse(idx) {
			throw IllegalStateException("Unknown enum value at index $idx for enum ${this.enumClass.canonicalName}.") }
	}

	override fun getNullValue(ctxt: DeserializationContext): E {
		return this.defaultValue
	}

	override fun getAbsentValue(ctxt: DeserializationContext): E {
		return this.defaultValue
	}

	companion object {
		fun registerModule(mapper: ObjectMapper): ObjectMapper {
			mapper.registerModule(SimpleModule().apply {
				setDeserializerModifier(object : BeanDeserializerModifier() {
					override fun modifyEnumDeserializer(
						config: DeserializationConfig,
						type: JavaType,
						beanDesc: BeanDescription?,
						deserializer: JsonDeserializer<*>
					): JsonDeserializer<*> {
						val rawClass = type.rawClass
						return if (rawClass != null && rawClass.isEnum) {
							@Suppress("UNCHECKED_CAST")
							OmittedEnumDeserializer(rawClass as Class<out Enum<*>>)
						} else deserializer
					}
				})
			})
			return mapper
		}
	}
}