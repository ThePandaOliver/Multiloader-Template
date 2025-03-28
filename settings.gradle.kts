pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.6-beta.1"
}

stonecutter {
    centralScript = "build.gradle.kts"
    create(rootProject) {
        versions("1.21.4")
        branch("fabric")
    }
}

rootProject.name = "Template"
