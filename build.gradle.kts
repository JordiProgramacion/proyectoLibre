plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.11"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/informative")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Testing
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.ui)
    implementation(compose.components.resources)

    // Mail recuperación
    implementation("com.sun.mail:jakarta.mail:2.0.2")
    // Funciones async
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    // Persistencia de datos
    implementation("com.google.code.gson:gson:2.10.1")
}

compose.desktop {
    application {
        mainClass = "app.MainKt"
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "proyectoLibre"
            packageVersion = "1.0.0"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}