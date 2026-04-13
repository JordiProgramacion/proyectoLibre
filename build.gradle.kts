plugins {
    kotlin("jvm") version "2.3.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
// mail recuperación
dependencies {
    implementation("com.sun.mail:jakarta.mail:2.0.2")
    // Funciones async
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    // Persistencia de datos
    implementation("com.google.code.gson:gson:2.10.1")
}