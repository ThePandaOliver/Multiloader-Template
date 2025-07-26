package com.example.template

import com.mojang.logging.LogUtils

object TemplateModCommon {
	const val MOD_ID: String = "template"

	init {
		LogUtils.getLogger().info("Hello from Init")
	}
}