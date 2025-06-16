package dev.uraxys.idleclient.tools.typescript.annotations

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientName(
	val value: String
)
