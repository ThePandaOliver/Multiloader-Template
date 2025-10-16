import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
	kotlin("jvm")
	id("dev.architectury.loom")
	id("architectury-plugin")
}

val mcVersion = stonecutter.current.version
val loaderPlatform: String = requireNotNull(findProperty("loom.platform") as? String) { "loom.platform property is required but not found" }

val modGroup: String by project
val modVersion: String by project
val modId: String by project

group = modGroup
version = modVersion
base { archivesName = "${modId}-${loaderPlatform}" }

architectury {
	when (loaderPlatform) {
		"fabric" -> fabric()
		"neoforge" -> neoForge()
	}
	platformSetupLoomIde()
	minecraft = mcVersion
}

loom {
	silentMojangMappingsLicense()
	log4jConfigs.from(rootProject.file("log4j2.xml"))
	accessWidenerPath = rootProject.file("src/main/resources/$modId.accesswidener")

	decompilers {
		get("vineflower").apply { // Adds names to lambdas - useful for mixins
			options.put("mark-corresponding-synthetics", "1")
		}
	}

	runs {
		named("client") {
			client()
			configName = "Client"
			runDir("../../.runs/client")
			programArg("--username=Dev")
			ideConfigGenerated(true)
		}
		named("server") {
			server()
			configName = "Server"
			runDir("../../.runs/server")
			ideConfigGenerated(true)
		}
	}
}

val nonModImplementation: Configuration by configurations.creating

configurations {
	implementation.get().extendsFrom(nonModImplementation)
	include.get().extendsFrom(nonModImplementation)
	if (loaderPlatform == "neoforge")
		getByName("forgeRuntimeLibrary").extendsFrom(nonModImplementation)
}

repositories {
	mavenCentral()
	maven("https://maven.architectury.dev/")
	maven("https://maven.fabricmc.net/")
	maven("https://maven.minecraftforge.net/")
	maven("https://maven.neoforged.net/releases/")
}

val loaderVersion: String by project

dependencies {
	val parchmentMinecraftVersion: String by project
	val parchmentMappingsVersion: String by project

	minecraft("com.mojang:minecraft:$mcVersion")
	mappings(loom.layered {
		officialMojangMappings()
		parchment("org.parchmentmc.data:parchment-$parchmentMinecraftVersion:$parchmentMappingsVersion@zip")
	})

	when (loaderPlatform) {
		"fabric" -> {
			val fabricApiVersion: String by project

			modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
			modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}")
		}

		"neoforge" -> {
			"neoForge"("net.neoforged:neoforge:${loaderVersion}")
		}
	}

	nonModImplementation(kotlin("stdlib"))
	nonModImplementation(kotlin("stdlib-jdk8"))
	nonModImplementation(kotlin("stdlib-jdk7"))
	nonModImplementation(kotlin("reflect", version = "2.2.0"))
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.10.2")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.8.1")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-io-core:0.7.0")
	nonModImplementation("org.jetbrains.kotlinx:kotlinx-io-bytestring:0.7.0")
}

val javaVersion: String by project

tasks {
	processResources {
		val modName: String by project
		val modDescription: String by project
		val modLicense: String by project
		val modAuthors: String by project

		val props = mutableMapOf(
			"java_version" to javaVersion,
			"minecraft_version" to mcVersion,

			"mod_version" to modVersion,
			"mod_group" to modGroup,
			"mod_id" to modId,

			"mod_name" to modName,
			"mod_description" to modDescription,
			"mod_license" to modLicense,
			"fabric_mod_authors" to modAuthors.split(",").joinToString(", ") { "\"$it\"" },
			"mod_authors" to modAuthors,

			"loader_version" to when (loaderPlatform) {
				"fabric" -> loaderVersion
				"neoforge" -> loaderVersion
				else -> null
			},
		)

		inputs.properties(props)
		filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml", "fabric.mod.json", "**.mixins.json", "pack.mcmeta")) {
			expand(props)
		}
	}

	remapJar {
		injectAccessWidener.set(true)
		if (loaderPlatform == "neoforge")
			atAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
	}
}

kotlin {
	jvmToolchain(javaVersion.toInt())
}

java {
	withSourcesJar()
}