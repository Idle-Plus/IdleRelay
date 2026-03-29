package dev.uraxys.idleclient.tools.typescript.annotations

import dev.uraxys.idleclient.network.types.GamePacket

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InternalPacket(
	val value: GamePacket
)