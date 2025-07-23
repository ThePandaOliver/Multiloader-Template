import org.gradle.kotlin.dsl.support.listFilesOrdered
import java.util.Properties

pluginManagement {
	repositories {
		mavenCentral()
		mavenLocal()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.neoforged.net/releases/")
	}
}

rootProject.name = "Template"

val mcVersion: String by settings

// Load version specific properties
fun loadVersionProperties() {
	val availableVersions = rootDir.resolve("versions")
		.listFilesOrdered { it.extension == "properties" } // Get all property files in order
		.filter { it.isFile }
		.map { it.nameWithoutExtension }
	require(availableVersions.isNotEmpty()) { "No versions found in versions directory" }
	require(mcVersion in availableVersions) { "Invalid Minecraft version: $mcVersion" }

	val props = Properties()
	props.load(rootDir.resolve("versions/$mcVersion.properties").inputStream())

	props.forEach { (k, v) ->
		if (k is String) {
			gradle.extra[k] = v
		}
	}
	gradle.extra["mcVersions"] = availableVersions
	gradle.extra["mcIndex"] = availableVersions.indexOf(mcVersion)
}
loadVersionProperties()

val buildFor: String by gradle.extra

// Add supported sub-projects
buildFor.split(",").forEach { platform ->
	include(platform)
}