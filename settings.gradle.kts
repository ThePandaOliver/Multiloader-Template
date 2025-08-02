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
	val availableVersions = rootDir.resolve("versionProperties")
		.listFiles { file -> file.extension == "properties" }
		.filter { it.isFile }
		.map { it.nameWithoutExtension }
		.sortedWith { v1, v2 ->
			val parts1 = v1.split(".").map { it.toInt() }
			val parts2 = v2.split(".").map { it.toInt() }

			for (i in 0 until maxOf(parts1.size, parts2.size)) {
				val part1 = parts1.getOrElse(i) { 0 }
				val part2 = parts2.getOrElse(i) { 0 }
				if (part1 != part2) return@sortedWith part1 - part2
			}
			0
		}

	require(availableVersions.isNotEmpty()) { "No versionProperties found in versionProperties directory" }
	require(mcVersion in availableVersions) { "Invalid Minecraft version: $mcVersion" }

	val props = Properties()
	props.load(rootDir.resolve("versionProperties/$mcVersion.properties").inputStream())

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