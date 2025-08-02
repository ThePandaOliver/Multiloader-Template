@file:Suppress("UnstableApiUsage")

val neoforgeLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-neoforge"

architectury {
	neoForge()
}

configurations.getByName("developmentNeoForge").extendsFrom(configurations.common.get())

repositories {
	maven("https://maven.neoforged.net/releases/")
}

dependencies {
	neoForge("net.neoforged:neoforge:$neoforgeLoaderVersion")

	common(project(":", "namedElements"))
	commonShadow(project(":", "transformProductionNeoForge"))
}

tasks {
	remapJar {
		atAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
	}
}