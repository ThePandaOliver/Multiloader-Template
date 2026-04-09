plugins {
	id("dev.architectury.loom")
}

val mcVersion = stonecutter.current.version
val loaderPlatform: String = requireNotNull(findProperty("loom.platform") as? String) { "loom.platform property is required but not found" }

val modGroup: String by project
val modVersion: String by project
val modId: String by project

group = modGroup
version = modVersion
base { archivesName = "${modId}-${loaderPlatform}" }

loom {
	silentMojangMappingsLicense()
	log4jConfigs.from(rootProject.file("log4j2.xml"))

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

val includeNoneMod: Configuration by configurations.creating

configurations {
	include.get().extendsFrom(includeNoneMod)
	if (loaderPlatform == "neoforge")
		getByName("forgeRuntimeLibrary").extendsFrom(includeNoneMod)
}

repositories {
	mavenCentral()
	maven("https://maven.fabricmc.net/")
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

tasks.withType(JavaCompile).configureEach {
	options.release = javaVersion
}