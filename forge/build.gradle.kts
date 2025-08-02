@file:Suppress("UnstableApiUsage")

val mcVersion: String by extra
val forgeLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-forge"

architectury {
	forge()
}

loom {
	forge {
		mixinConfig("template-forge.mixins.json")
		mixinConfig("template-common.mixins.json")
	}
}

configurations.getByName("developmentForge").extendsFrom(configurations.common.get())

repositories {
    maven("https://maven.minecraftforge.net")
}

dependencies {
	forge("net.minecraftforge:forge:$mcVersion-$forgeLoaderVersion")

	common(project(":", "namedElements"))
	commonShadow(project(":", "transformProductionForge"))
}

tasks {
	remapJar {
		atAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
	}
}