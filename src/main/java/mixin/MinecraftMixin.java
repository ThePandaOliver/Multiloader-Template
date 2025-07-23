package com.example.template.mixin;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private static void init(CallbackInfo info) {
		#if MC_VER == MC_1_21_8
		LogUtils.getLogger().info("Hello from 1.21.8");
		#elif MC_VER == MC_1_21_7
		LogUtils.getLogger().info("Hello from 1.21.7");
		#endif
	}
}
