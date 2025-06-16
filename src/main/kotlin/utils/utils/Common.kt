package dev.uraxys.idleclient.utils.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object Common {

	/**
	 * Used when parsing messages from the server.
	 */
	val RELAY_INBOUND_NETWORK_JSON: ObjectMapper = ObjectMapper()
		.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
		.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
		//.enable(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX)
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		//.configure(EnumFeature.READ_ENUM_KEYS_USING_INDEX, true)
		.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT)
		.registerKotlinModule()
		.registerModule(JavaTimeModule())

	/**
	 * Used when sending messages to the client.
	 */
	val RELAY_OUTBOUND_NETWORK_JSON: ObjectMapper = ObjectMapper()
		.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
		.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
		//.enable(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX)
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		//.configure(EnumFeature.READ_ENUM_KEYS_USING_INDEX, true)
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