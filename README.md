# What is this?
This is a template for minecraft modding projects, 
it uses [Architectury Loom](https://docs.architectury.dev/loom/introduction) for easy multi-loader management, 
[Stonecutter](https://stonecutter.kikugie.dev/) for multi-version management,
and [Forgix](https://github.com/PacifistMC/Forgix) for merging jars into one jar.\
This template was made with Fabric, Forge and NeoForge support in mind

## [Architectury Loom](https://docs.architectury.dev/loom/introduction)
Architectury Loom is a replacement for [ForgeGradle](https://docs.minecraftforge.net/en/fg-6.x/) and
[NeoGradle](https://docs.neoforged.net/neogradle/docs/) and a fork of [Fabric Loom](https://docs.fabricmc.net/develop/loom/).\

[Fabric Loom](https://docs.fabricmc.net/develop/loom/) has a lot of useful, features making mod development much easier, 
the only downside is that it was made only for the Fabric loader. But luckily the Architectury team has made a version that can be 
used for mod development for Forge and NeoForge, making multiloader development much easier.

## [Stonecutter](https://stonecutter.kikugie.dev/)
Stonecutter is gradle plugin for easy multi-version management, it adds pre-processing functionality to java made 
specifically for version management, allowing you to write all code for all versions in one instance of your project.\
Read [this guide](https://stonecutter.kikugie.dev/stonecutter/guide/comments) to learn how to use Stonecutter's pre-processing.

## [Forgix](https://github.com/PacifistMC/Forgix)
Forgix is a gradle plugin that allows you to merge jars together, creating one jar that works on all supported loaders.\
Note: this does not merge different versions, it only merges the different loader versions.

# How do i set up my project?
Comming soon
