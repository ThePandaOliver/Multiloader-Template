package com.example.template;

import com.mojang.logging.LogUtils;

public class TemplateModCommon {
	public static final String MOD_ID = "template";

	public static void init() {
		LogUtils.getLogger().info("Hello from Init");
	}
}
