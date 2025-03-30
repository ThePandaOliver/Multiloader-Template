plugins {
    `kotlin-dsl`
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
	gradlePluginPortal()
}

dependencies {
	// Make sure the version here is the same as the plugin in settings.gradle.kts
	implementation("dev.kikugie.stonecutter:dev.kikugie.stonecutter.gradle.plugin:0.5.1")
}