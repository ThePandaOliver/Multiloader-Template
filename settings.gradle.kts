pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.architectury.dev/") { name = "Architectury" }
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.minecraftforge.net/") { name = "Forge" }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForge" }
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.9"
}

rootProject.name = "Template"

stonecutter {
	create(rootProject) {
		fun mc(mcVersion: String, name: String = mcVersion, loaders: Iterable<String>, buildscriptName: String? = null) {
			for (loader in loaders) {
				version("$name-$loader", mcVersion).apply {
					if (buildscriptName != null) {
						buildscript(buildscriptName)
					}
				}
			}
		}

		mc("1.21.11", loaders = listOf("fabric", "neoforge"), buildscriptName = "obfuscated")
		mc("26.1.2", loaders = listOf("fabric", "neoforge"))
	}
}
