plugins {
	id("dev.kikugie.stonecutter")
	id("dev.architectury.loom") version "1.13-SNAPSHOT" apply false
	id("dev.architectury.loom-no-remap") version "1.14-SNAPSHOT" apply false
}
stonecutter active "26.1.2-fabric"

stonecutter parameters {
	constants {
		match(node.metadata.project.substringAfterLast("-"), "fabric", "neoforge", "forge")
	}
}

val modGroup: String by project
val modId: String by project