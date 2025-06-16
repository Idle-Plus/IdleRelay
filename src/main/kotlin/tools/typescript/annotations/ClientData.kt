package dev.uraxys.idleclient.tools.typescript.annotations

/**
 * Marks a class as something the client should know about, resulting in a
 * TypeScript interface being generated for it.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientData(
	/**
	 * Overrides the name of the generated data.
	 *
	 * Colon (`:`) can be used to insert a dollar sign (`$`), which is a valid
	 * character in TypeScript identifiers. This is mostly useful for types
	 * which have a common name, for example `Result`, which could have
	 * multiple implementations, such as `LoginResult`, `SwitchResult`, etc.
	 *
	 * In these cases, the name can be overridden to for example `Login:Result`,
	 * which will result in the generated type being named `Login$Result`.
	 */
	val type: String = ""
) {
	companion object {
		/**
		 * Returns the name of the type, with the colon replaced by a dollar
		 * sign.
		 */
		fun parse(type: String): String {
			return type.replace(':', '$')
		}
	}
}
