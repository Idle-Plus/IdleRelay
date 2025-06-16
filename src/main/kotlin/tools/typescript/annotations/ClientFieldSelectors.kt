package dev.uraxys.idleclient.tools.typescript.annotations

/**
 * Marks the field as ignored when used in TypeScript generation.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientIgnore

/**
 * Marks the class as requiring @ClientInclude to include properties and fields.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientIncludeOnly

/**
 * Marks the field as included when used in TypeScript generation.
 *
 * Must be used with @ClientIncludeOnly.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientInclude