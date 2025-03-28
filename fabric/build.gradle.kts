@file:Suppress("UnstableApiUsage")

plugins {
	id("dev.architectury.loom")
	id("architectury-plugin")
	id("com.github.johnrengelman.shadow")
}

val loader = prop("loom.platform")!!
val minecraft: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("")) {
	"No common project for $project"
}.project

version = "${mod.version}+$minecraft"
base {
	archivesName.set("${mod.id}-$loader")
}

architectury {
	platformSetupLoomIde()
	fabric()
}

loom {
	silentMojangMappingsLicense()

	decompilers {
		get("vineflower").apply { // Adds names to lambdas - useful for mixins
			options.put("mark-corresponding-synthetics", "1")
		}
	}

	runs {
		val runDir = "../../../.runs"

		named("client") {
			client()
			configName = "Client"
			runDir("$runDir/client")
			source(sourceSets["main"])
			programArgs("--username=Dev")
		}
		named("server") {
			server()
			configName = "Server"
			runDir("$runDir/server")
			source(sourceSets["main"])
		}
	}

	runConfigs.all {
		isIdeConfigGenerated = true
	}
}

val commonBundle: Configuration by configurations.creating {
	isCanBeConsumed = false
	isCanBeResolved = true
}

val shadowBundle: Configuration by configurations.creating {
	isCanBeConsumed = false
	isCanBeResolved = true
}

configurations {
	compileClasspath.get().extendsFrom(commonBundle)
	runtimeClasspath.get().extendsFrom(commonBundle)
	get("developmentFabric").extendsFrom(commonBundle)
}

repositories {
	maven("https://maven.parchmentmc.org/")
}

dependencies {
	minecraft("com.mojang:minecraft:$minecraft")
	mappings(loom.layered {
		officialMojangMappings()
		parchment(
			"org.parchmentmc.data:parchment-${common.mod.version("parchment_minecraft_version")}:${common.mod.version("parchment_mappings_version")}@zip"
		)
	})
	modImplementation("net.fabricmc:fabric-loader:${common.mod.version("fabric_loader")}")

	commonBundle(project(common.path, "namedElements")) { isTransitive = false }
	shadowBundle(project(common.path, "transformProductionFabric")) { isTransitive = false }
}

tasks.processResources {
	properties(
		listOf("fabric.mod.json"),

		"mod_id" to mod.id,
		"mod_version" to mod.version,
		"minecraft_version" to minecraft,
		"fabric_version" to mod.version("fabric_loader"),
	)
}

tasks.shadowJar {
	configurations = listOf(shadowBundle)
	archiveClassifier = "dev-shadow"
}

tasks.remapJar {
	injectAccessWidener = true
	input = tasks.shadowJar.get().archiveFile
	archiveClassifier = null
	dependsOn(tasks.shadowJar)
}

tasks.jar {
	archiveClassifier = "dev"
}

java {
	withSourcesJar()
	val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
		JavaVersion.VERSION_21 else JavaVersion.VERSION_17
	targetCompatibility = java
	sourceCompatibility = java
}

tasks.build {
	group = "versioned"
	description = "Must run through 'chiseledBuild'"
}