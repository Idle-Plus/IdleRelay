package dev.uraxys.idleclient.utils.extensions

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

fun KAnnotatedElement.has(clazz: KClass<out Annotation>): Boolean {
	return this.annotations.any { it.annotationClass == clazz }
}