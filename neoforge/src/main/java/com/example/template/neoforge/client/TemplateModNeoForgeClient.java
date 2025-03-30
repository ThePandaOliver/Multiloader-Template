package com.example.template.neoforge.client;

import com.example.template.TemplateModCommon;
import com.example.template.client.TemplateModCommonClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = TemplateModCommon.MOD_ID, dist = Dist.CLIENT)
public class TemplateModNeoForgeClient {
	public TemplateModNeoForgeClient() {
		TemplateModCommonClient.init();
	}
}
