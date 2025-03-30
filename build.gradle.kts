@file:Suppress("UnstableApiUsage")

plugins {
	id("dev.architectury.loom")
	id("architectury-plugin")
	id("io.github.pacifistmc.forgix")
}

val minecraft = stonecutter.current.version

version = "${prop("mod.version")}+$minecraft"
base {
	archivesName.set("${prop("mod.id")}-common")
}

forgix {
	val version = "${prop("mod.version")}+${minecraft}"
	group = "${prop("mod.group")}.${prop("mod.id")}"
	mergedJarName = "${prop("mod.id")}-${version}.jar"
	outputDir = "build/libs/merged"

	if (findProject(":fabric") != null) {
		fabricContainer = FabricContainer().apply {
			jarLocation = "versions/${minecraft}/build/libs/${prop("mod.id")}-fabric-${version}.jar"
		}
	}

	if (findProject(":forge") != null) {
		forgeContainer = ForgeContainer().apply {
			jarLocation = "versions/${minecraft}/build/libs/${prop("mod.id")}-forge-${version}.jar"
		}
	}

	if (findProject(":neoforge") != null) {
		neoForgeContainer = NeoForgeContainer().apply {
			jarLocation = "versions/${minecraft}/build/libs/${prop("mod.id")}-neoforge-${version}.jar"
		}
	}

	removeDuplicate("${prop("mod.group")}.${prop("mod.id")}")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
	if (stonecutter.current.project !in it) null
	else it.project.prop("loom.platform")
})

loom {
	silentMojangMappingsLicense()
	accessWidenerPath = rootProject.file("src/main/resources/${prop("mod.id")}.accesswidener")

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
		parchment("org.parchmentmc.data:parchment-${versionProp("parchment_minecraft_version")}:${versionProp("parchment_mappings_version")}@zip")
	})
	modImplementation("net.fabricmc:fabric-loader:${versionProp("fabric_loader")}")
}

tasks.processResources {
	applyProperties(project, listOf("${prop("mod.id")}-common.mixin.json"))
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
