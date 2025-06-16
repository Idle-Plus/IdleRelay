package dev.uraxys.idleclient.tools.typescript.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientDoc(
	val value: String
)
