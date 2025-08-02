package com.example.template.forge.client;

import com.example.template.client.TemplateModCommonClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TemplateModForgeClient {
	public TemplateModForgeClient() {
		TemplateModCommonClient.init();
	}
}
