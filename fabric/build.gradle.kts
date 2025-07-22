@file:Suppress("UnstableApiUsage")

val fabricLoaderVersion: String by extra

base.archivesName = "${rootProject.base.archivesName.get()}-fabric"

architectury {
	platformSetupLoomIde()
	fabric()
}

configurations {
	compileClasspath.get().extendsFrom(commonBundle.get())
	runtimeClasspath.get().extendsFrom(commonBundle.get())
	get("developmentFabric").extendsFrom(commonBundle.get())
}

dependencies {
	modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

	commonBundle(project(":", "namedElements")) { isTransitive = false }
	shadowBundle(project(":", "transformProductionFabric")) { isTransitive = false }
}