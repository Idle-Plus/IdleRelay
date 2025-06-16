package dev.uraxys.idleclient.utils

import java.io.File

object Utils {

	val development = System.getenv("DEVELOPMENT")?.toBoolean() == true
	val userDir = path(System.getProperty("user.dir"), if (development) "backend/run" else "")

	fun getMillis(): Long {
		return getNanos() / 1_000_000L
	}

	fun getNanos(): Long {
		return System.nanoTime()
	}

	fun path(parent: String, path: String): String {
		if (path.isEmpty()) return parent
		return "$parent${File.separatorChar}$path".replace("/", File.separator)
	}

	fun file(parent: String, path: String): File {
		return File(parent, path.replace("/", File.separator))
	}

	fun file(parent: File, path: String): File {
		return File(parent, path.replace("/", File.separator))
	}
}