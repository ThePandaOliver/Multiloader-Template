@file:Suppress("UnstableApiUsage")

plugins {
	java
	idea

	id("dev.architectury.loom") version "1.10-SNAPSHOT"
	id("architectury-plugin") version "3.4-SNAPSHOT"
	id("io.github.pacifistmc.forgix") version "2.0.0-SNAPSHOT.5.1"
}

allprojects {
	rootProject.gradle.extra.properties.forEach { (key, value) ->
		project.ext.set(key, value)
	}
}

val mcVersion: String by extra
val buildFor: String by extra

val modVersion: String by extra
val modGroup: String by extra
val modId: String by extra

val modName: String by extra
val modDescription: String by extra
val modAuthors: String by extra
val modLicense: String by extra

val parchmentMinecraftVersion: String by extra
val parchmentMappingVersion: String by extra

architectury {
	common(buildFor.split(","))
}

loom {
	runConfigs.configureEach { isIdeConfigGenerated = false }
}

allprojects {
	apply(plugin = "java")
	apply(plugin = "dev.architectury.loom")
	apply(plugin = "architectury-plugin")

	version = "$modVersion+$mcVersion"
	group = modGroup

	loom {
		silentMojangMappingsLicense()
		accessWidenerPath = rootProject.file("src/main/resources/$modId.accesswidener")

		decompilers {
			get("vineflower").apply { // Adds names to lambdas - useful for mixins
				options.put("mark-corresponding-synthetics", "1")
			}
		}
	}

	repositories {
		maven("https://maven.parchmentmc.org/")
	}

	dependencies {
		minecraft("com.mojang:minecraft:$mcVersion")
		mappings(loom.layered {
			officialMojangMappings()
			parchment("org.parchmentmc.data:parchment-$parchmentMinecraftVersion:$parchmentMappingVersion@zip")
		})

		annotationProcessor(rootProject.libs.preprocessor)
	}

	java {
		withSourcesJar()
		val java = JavaVersion.VERSION_21
		targetCompatibility = java
		sourceCompatibility = java
	}

	tasks.withType(JavaCompile::class).configureEach {
		options.compilerArgs.add("-Xplugin:Manifold")
	}
}

subprojects {
	val commonBundle: Configuration by configurations.creating {
		isCanBeConsumed = false
		isCanBeResolved = true
	}

	val shadowBundle: Configuration by configurations.creating {
		isCanBeConsumed = false
		isCanBeResolved = true
	}

	dependencies {
	}

	tasks.processResources {
		val props = mutableMapOf(
			"minecraft_version" to mcVersion,

			"mod_version" to modVersion,
			"mod_group" to modGroup,
			"mod_id" to modId,

			"mod_name" to modName,
			"mod_description" to modDescription,
			"mod_license" to modLicense,
			"mod_authors_fabric" to modAuthors.split(",").joinToString(", ") { "\"$it\"" },
			"mod_authors_forge" to modAuthors,
		)

		inputs.properties(props)
		filesMatching(listOf("META-INF/neoforge.mods.toml", "fabric.mod.json", "*.mixin.json", "pack.mcmeta")) {
			expand(props)
		}
	}

	loom {
		runs {
			val runDir = "../../../.runs"

			named("client") {
				client()
				configName = "Client"
				runDir("$runDir/client")
				source(sourceSets["main"])
				programArgs("--username=Dev")
				isIdeConfigGenerated = true
			}
			named("server") {
				server()
				configName = "Server"
				runDir("$runDir/server")
				source(sourceSets["main"])
				isIdeConfigGenerated = true
			}
		}
	}

	tasks.remapJar {
		injectAccessWidener = true
	}
}