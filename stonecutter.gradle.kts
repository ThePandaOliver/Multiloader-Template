import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings

plugins {
	id("dev.kikugie.stonecutter")
	kotlin("jvm") version "2.2.0" apply false
	id("architectury-plugin") version "3.4-SNAPSHOT" apply false
	id("dev.architectury.loom") version "1.11-SNAPSHOT" apply false
	id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.10"
}
stonecutter active "1.21.10-fabric"

stonecutter parameters {
	constants {
		match(node.metadata.project.substringAfterLast("-"), "fabric", "neoforge", "forge")
	}
}

val modGroup: String by project
val modId: String by project

idea {
	module {
		settings {
			val packagePrefixStr = "$modGroup.$modId"
			packagePrefix["src/main/kotlin"] = packagePrefixStr
			packagePrefix["src/main/java"] = packagePrefixStr
		}
	}
}