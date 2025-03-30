package com.example.template.neoforge.client;

import com.example.template.TemplateModCommon;
import com.example.template.client.TemplateModCommonClient;
import net.neoforged.fml.common.Mod;

@Mod(value = TemplateModCommon.MOD_ID /*? if >1.20.5 {*/ /*, dist = Dist.CLIENT *//*?}*/)
public class TemplateModNeoForgeClient {
	public TemplateModNeoForgeClient() {
		TemplateModCommonClient.init();
	}
}
