@file:Suppress("UnstableApiUsage")

plugins {
	alias(libs.plugins.neoforgeModdev)
}

val parchmentMinecraftVersion: String by extra
val parchmentMappingVersion: String by extra
val neoforgeLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-neoforge"

neoForge {
	version = neoforgeLoaderVersion

	validateAccessTransformers = true

	parchment {
		minecraftVersion = parchmentMinecraftVersion
		mappingsVersion = parchmentMappingVersion
	}

	runs {
		register("client") {
			client()
			gameDirectory.set(file("../.runs"))
		}
		register("server") {
			server()
			gameDirectory.set(file("../.runs"))
		}
	}
}

configurations {
	configurations["additionalRuntimeClasspath"].extendsFrom(common.get())
}

tasks {
	jar {
		archiveClassifier = "dev"
		finalizedBy("shadowJar")
	}
}