@file:Suppress("UnstableApiUsage")

plugins {
	id("dev.architectury.loom")
	id("architectury-plugin")
	id("io.github.pacifistmc.forgix")
}

val minecraft = stonecutter.current.version

version = "${mod.version}+$minecraft"
base {
	archivesName.set("${mod.id}-common")
}

forgix {
	val version = "${mod.version}+${minecraft}"
	group = "${mod.group}.${mod.id}"
	mergedJarName = "${mod.id}-${version}.jar"
	outputDir = "build/libs/merged"

	if (findProject(":fabric") != null) {
		fabricContainer = FabricContainer().apply {
			jarLocation = "versions/${minecraft}/build/libs/${mod.id}-fabric-${version}.jar"
		}
	}

	if (findProject(":forge") != null) {
		forgeContainer = ForgeContainer().apply {
			jarLocation = "versions/${minecraft}/build/libs/${mod.id}-forge-${version}.jar"
		}
	}

	if (findProject(":neoforge") != null) {
		neoForgeContainer = NeoForgeContainer().apply {
			jarLocation = "versions/${minecraft}/build/libs/${mod.id}-neoforge-${version}.jar"
		}
	}

	removeDuplicate("${mod.group}.${mod.id}")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
	if (stonecutter.current.project !in it) null
	else it.project.prop("loom.platform")
})

loom {
	silentMojangMappingsLicense()
	accessWidenerPath = rootProject.file("src/main/resources/${mod.id}.accesswidener")

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
	minecraft("com.mojang:minecraft:$minecraft")
	mappings(loom.layered {
		officialMojangMappings()
		parchment("org.parchmentmc.data:parchment-${mod.version("parchment_minecraft_version")}:${mod.version("parchment_mappings_version")}@zip")
	})
	modImplementation("net.fabricmc:fabric-loader:${mod.version("fabric_loader")}")
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
