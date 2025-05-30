pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.neoforged.net/releases/")
//		maven("https://maven.kikugie.dev/snapshots")
	}
}

plugins {
	// Make sure the version here is the same as the dependency in buildSrc/build.gradle.kts
	id("dev.kikugie.stonecutter") version "0.5.1"
}

stonecutter {
	centralScript = "build.gradle.kts"
	kotlinController = true
	create(rootProject) {
		versions("1.20", "1.20.5", "1.21", "1.21.2", "1.21.4")
		vcsVersion = "1.21.4"
		branch("fabric")
		branch("forge") { versions("1.20") }
		branch("neoforge") { versions("1.20.5", "1.21", "1.21.2", "1.21.4") }
	}
}

rootProject.name = "Template"
