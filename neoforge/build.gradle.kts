@file:Suppress("UnstableApiUsage")

plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

val neoforgeLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName}-fabric"

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