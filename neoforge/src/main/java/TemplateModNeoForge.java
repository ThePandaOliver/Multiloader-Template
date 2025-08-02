package com.example.template.neoforge;

import com.example.template.TemplateModCommon;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(TemplateModCommon.MOD_ID)
public class TemplateModNeoForge {
	public TemplateModNeoForge() {
		TemplateModCommon.init();

		#if MC_VER < MC_1_21
		if (FMLEnvironment.dist.isClient()) {
			// We use the full path reference to avoid class not found reference
			new com.example.template.neoforge.client.TemplateModNeoForgeClient();
		}
		#endif
	}
}
