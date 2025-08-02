package com.example.template.neoforge.client;

import com.example.template.TemplateModCommon;
import com.example.template.client.TemplateModCommonClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.Mod;

#if MC_VER >= MC_1_21
@Mod(value = TemplateModCommon.MOD_ID, dist = Dist.CLIENT)
#endif
@OnlyIn(Dist.CLIENT)
public class TemplateModNeoForgeClient {
	public TemplateModNeoForgeClient() {
		TemplateModCommonClient.init();
	}
}
