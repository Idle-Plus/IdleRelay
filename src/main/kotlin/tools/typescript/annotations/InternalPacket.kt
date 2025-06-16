package dev.uraxys.idleclient.tools.typescript.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InternalPacket(
	val value: Int
)