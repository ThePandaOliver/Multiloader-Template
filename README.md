# Minecraft Modding Template V2

This template helps you create Minecraft mods that work with Fabric, Forge, and NeoForge. It includes:

- **[Fabric Loom](https://github.com/FabricMC/fabric-loom)** – Is used for compiling and providing the Minecraft codebase for the Fabric mod
- **[NeoForge ModDev](https://github.com/neoforged/ModDevGradle)** – Is used for compiling and providing the Minecraft codebase for the NeoForge mod and common
  codebase via NeoForm
- **[Manifold Pre-processing](https://github.com/manifold-systems/manifold/tree/master/manifold-deps-parent/manifold-preprocessor/)** – Adds pre-processing
  functionality to Java. Will be used for multi-version support without the need of separate branches or sub-packages.
- **[Forgix](https://github.com/PacifistMC/Forgix)** – Can combine all supported Minecraft versions and mod-loader variants of the mod into a single jar.

> For the best development experience then its recommeneded to use IntelliJ IDEA and installing
> the [Manifold extension](https://plugins.jetbrains.com/plugin/10057-manifold-ij)

> Support for combining multiple versions via Forgix is still experimental

---

# Setting Up Your Project

It's assumed that you know the basics of minecraft modding and java programming.

> Outdated

### 1. **Edit `gradle.properties`**

Update the following values:

- **`mod.version`** – Your mod's version.
- **`mod.group`** – Your mod's unique identifier (its common practice to use your own domain, e.g., `com.example` if you own the domain `example.com`). If not,
  use `me.<yourname>`.
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