@file:Suppress("UnstableApiUsage")

val neoforgeLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-neoforge"

architectury {
    platformSetupLoomIde()
    neoForge()
}

configurations {
    compileClasspath.get().extendsFrom(commonBundle.get())
    runtimeClasspath.get().extendsFrom(commonBundle.get())
    get("developmentNeoForge").extendsFrom(commonBundle.get())
}

repositories {
	maven("https://maven.neoforged.net/releases/")
}

dependencies {
    neoForge("net.neoforged:neoforge:$neoforgeLoaderVersion")

    commonBundle(project(":", "namedElements")) { isTransitive = false }
    shadowBundle(project(":", "transformProductionNeoForge")) { isTransitive = false }
}