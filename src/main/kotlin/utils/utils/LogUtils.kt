package dev.uraxys.idleclient.utils.utils

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.KClass

object LogUtils {

	fun create(klass: KClass<*>): Logger {
		return LogManager.getLogger(klass.java)
	}

	fun create(klass: Class<*>): Logger {
		return LogManager.getLogger(klass)
	}

	fun create(name: String): Logger {
		return LogManager.getLogger(name)
	}

}