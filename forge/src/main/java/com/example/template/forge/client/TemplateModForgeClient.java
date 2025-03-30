package com.example.template.forge.client;

import com.example.template.client.TemplateModCommonClient;

// Dont annotate with @OnlyIn(Dist.CLIENT)
public class TemplateModForgeClient {
	public TemplateModForgeClient() {
		TemplateModCommonClient.init();
	}
}
