@file:Suppress("UnstableApiUsage")

plugins {
	alias(libs.plugins.fabricLoom)
}

val mcVersion: String by extra
val modId: String by extra

val parchmentMinecraftVersion: String by extra
val parchmentMappingVersion: String by extra
val fabricLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-fabric"

loom {
	runs {
		val runDir = "../.runs"

		named("client") {
			client()
			configName = "Client"
			runDir("$runDir/client")
			programArgs("--username=Dev")
			isIdeConfigGenerated = true
		}
		named("server") {
			server()
			configName = "Server"
			runDir("$runDir/server")
			isIdeConfigGenerated = true
		}
	}

	accessWidenerPath = file("src/main/resources/$modId.accesswidener")

	decompilers {
		get("vineflower").apply { // Shows the method name of lambdas in a comment
			options.put("mark-corresponding-synthetics", "1")
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:$mcVersion")
	mappings(loom.layered {
		officialMojangMappings()
		parchment("org.parchmentmc.data:parchment-$parchmentMinecraftVersion:$parchmentMappingVersion@zip")
	})
	modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
}

tasks {
	remapJar {
		inputFile = shadowJar.get().archiveFile
	}

	shadowJar {
		archiveClassifier = "dev-shadow"
	}
}