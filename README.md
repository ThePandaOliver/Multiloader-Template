# Minecraft Modding Template

This template helps you create Minecraft mods that work with Fabric, Forge, and NeoForge. It includes:
- **[Architectury Loom](https://docs.architectury.dev/loom/introduction)** – Manages multiple mod loaders easily.
- **[Stonecutter](https://stonecutter.kikugie.dev/)** – Handles multiple Minecraft versions in one project.
- **[Forgix](https://github.com/PacifistMC/Forgix)** – Combines different loader versions into a single jar.

## Tools Overview

### **[Architectury Loom](https://docs.architectury.dev/loom/introduction)**
A Gradle tool that simplifies multi-loader mod development. It extends **Fabric Loom**, allowing you to develop mods for **Fabric, Forge, and NeoForge** in one project.

### **[Stonecutter](https://stonecutter.kikugie.dev/)**
A Gradle plugin for managing multiple Minecraft versions. It lets you write code for different versions in one project. Learn more [here](https://stonecutter.kikugie.dev/stonecutter/guide/comments).

### **[Forgix](https://github.com/PacifistMC/Forgix)**
A Gradle plugin that merges different loader versions into a single jar. **Note:** It doesn’t merge different Minecraft versions, only different loaders.

---

# Setting Up Your Project

It's assumed that you know the basics of minecraft modding and java programming.

### 1. **Edit `gradle.properties`**
Update the following values:
- **`mod.version`** – Your mod's version.
- **`mod.group`** – Your mod's unique identifier (its common practice to use your own domain, e.g., `com.example` if you own the domain `example.com`). If not, use `me.<yourname>`.
- **`mod.id`** – Your mod's ID.
- **`mod.name`** – Display name of your mod (spaces allowed).
- **`mod.description`** – A short description (spaces allowed).
- **`mod.license`** – License abbreviation (e.g., `MIT`).

### 2. **Update Package Names**
- Inside `src/main/java` for the root, Fabric, Forge, and NeoForge projects:
	- Rename `com.example` to your mod's group (`mod.group`).
	- Rename `template` to your `mod.id`.

### 3. **Rename Resources**
Inside the `src/main/resources` for the root, Fabric, Forge, and NeoForge projects, update:
- **Files:** 
  - Rename the `template` part of `template-*.mixin.json` files to match your `mod.id`.
  - Rename the `template` part of `template.accesswidener` files to match your `mod.id`.
- **`architectury.common.json` file:** Update the `accessWidener` value to match your new `.accesswidener` filename.
- **Assets folder:** Rename `template` to your `mod.id`.

### 4. **Final Changes**
- **Update `TemplateModCommon` class** – Set your mod ID.
- **Edit `settings.gradle.kts`** – Set `rootProject.name` to your project’s name.

That’s it! Your modding project is now set up and ready to go. 🚀