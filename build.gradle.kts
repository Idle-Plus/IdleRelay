plugins {
	kotlin("jvm") version "2.1.20"
	id("io.ktor.plugin") version "3.1.3"
	application
}

group = "dev.uraxys.idleclient"
version = "1.0"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(kotlin("test"))

	// Log4j2
	implementation("org.apache.logging.log4j:log4j-core:2.24.0")
	implementation("org.apache.logging.log4j:log4j-api:2.24.0")
	runtimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.0")

	// Ktor
	implementation("io.ktor:ktor-server-core:3.1.3")
	implementation("io.ktor:ktor-server-websockets:3.1.3")
	implementation("io.ktor:ktor-server-netty:3.1.3")
	implementation("io.ktor:ktor-network-tls-certificates:3.1.3")

	// Netty
	implementation("io.netty:netty-all:4.1.115.Final")
	// Kotlin Reflection
	implementation(kotlin("reflect"))
	// Kotlin Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

	// ClassGraph
	implementation("io.github.classgraph:classgraph:4.8.177")

	// Jackson
	implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
}

application {
	// Configure the main class
	mainClass.set("dev.uraxys.idleclient.MainKt")
}


tasks.test {
	useJUnitPlatform()
}
kotlin {
	jvmToolchain(21)
}