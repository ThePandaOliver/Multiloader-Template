package com.example.template

//? if fabric {
import net.fabricmc.api.ModInitializer
//?}
//? if neoforge {
/*import net.neoforged.fml.common.Mod
*///?}


internal const val MOD_ID = "template"

internal fun initializeMod() {
	println("Template Mod has been initialized.")
}

//? if fabric {
class TemplateModFabric : ModInitializer {
	override fun onInitialize() {
		initializeMod()
	}
}
//?}

//? if neoforge {
/*@Mod(MOD_ID)
class TemplateModNeoForge {
	init {
		initializeMod()
	}
}
*///?}