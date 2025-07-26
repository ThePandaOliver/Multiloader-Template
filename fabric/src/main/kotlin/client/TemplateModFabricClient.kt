package com.example.template.fabric.client

import com.example.template.client.TemplateModCommonClient
import net.fabricmc.api.ClientModInitializer

class TemplateModFabricClient : ClientModInitializer {
	override fun onInitializeClient() {
		TemplateModCommonClient
	}
}
