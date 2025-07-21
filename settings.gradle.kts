import java.util.Properties

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.neoforged.net/releases/")
	}
}

rootProject.name = "Template"

include("fabric")
include("neoforge")

val mcVersion: String by settings

fun loadVersionProperties() {
	val props = Properties()
	props.load(rootDir.resolve("versions/$mcVersion.properties").inputStream())

	props.forEach { (k, v) ->
		if (k is String) {
			gradle.extra[k] = v
		}
	}
}
loadVersionProperties()