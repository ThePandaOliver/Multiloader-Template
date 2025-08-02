@file:Suppress("UnstableApiUsage")

val mcVersion: String by extra
val forgeLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-forge"

architectury {
	forge()
}

configurations.getByName("developmentForge").extendsFrom(configurations.common.get())

repositories {
    maven("https://maven.minecraftforge.net")
}

dependencies {
	forge("net.minecraftforge:forge:$mcVersion-$forgeLoaderVersion")

	common(project(":", "namedElements")) { isTransitive = false }
	commonShadow(project(":", "transformProductionForge")) { isTransitive = false }
}

tasks {
	remapJar {
		atAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
	}
}