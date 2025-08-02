@file:Suppress("UnstableApiUsage")

val fabricLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-fabric"

architectury {
	fabric()
}

configurations.getByName("developmentFabric").extendsFrom(configurations.common.get())

dependencies {
	modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

	common(project(":", "namedElements"))
	commonShadow(project(":", "transformProductionFabric"))
}