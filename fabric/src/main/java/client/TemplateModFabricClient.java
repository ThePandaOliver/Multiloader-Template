package com.example.template.fabric.client;

import com.example.template.client.TemplateModCommonClient;
import net.fabricmc.api.ClientModInitializer;

public class TemplateModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TemplateModCommonClient.init();
	}
}
