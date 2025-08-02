package com.example.template.forge;

import com.example.template.TemplateModCommon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(TemplateModCommon.MOD_ID)
public class TemplateModForge {
	public TemplateModForge() {
		TemplateModCommon.init();

		if (FMLEnvironment.dist == Dist.CLIENT) {
			// We use the full path reference to avoid class not found reference
			new com.example.template.forge.client.TemplateModForgeClient();
		}
	}
}
