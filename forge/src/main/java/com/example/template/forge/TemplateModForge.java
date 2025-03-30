package com.example.template.forge;

import com.example.template.TemplateModCommon;
import com.example.template.client.TemplateModCommonClient;
import com.example.template.forge.client.TemplateModForgeClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(TemplateModCommon.MOD_ID)
public class TemplateModForge {
	public TemplateModForge() {
		TemplateModCommon.init();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TemplateModForgeClient::new);
	}
}
