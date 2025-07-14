# `build-logic` Module

The `build-logic` module is a Gradle project in itself, specifically designed to host
project-specific Gradle Convention Plugins. Its primary purpose is to centralize and encapsulate
reusable build logic, ensuring consistency and maintainability across all other modules in your
multi-module project.

## Purpose and Importance

This module tackles the challenge of managing common build configurations in complex multi-module
setups by enabling the definition of reusable convention plugins.

The key benefits include:

- **Single Source of Truth:** All shared configurations, such as dependency versions, Android build
  settings (SDK versions, build types), Kotlin compiler options, and specific plugin applications
  (e.g., Hilt, Compose, Room), are defined in one place.

- **Reduced Boilerplate:** Drastically cuts down on the size and complexity of individual module
  build.gradle.kts files, making them cleaner and easier to read.

- **Enhanced Consistency:** Guarantees that all modules adhere to the same build standards and best
  practices automatically when the relevant convention plugin is applied.

- **Simplified Maintenance:** Updating a shared dependency or configuration only requires a change
  in the build-logic module, which then propagates automatically to all dependent modules.

### Synergy with Gradle Version Catalogs

The build-logic module seamlessly integrates with Gradle Version Catalogs. Convention plugins define
how modules are configured and what dependencies they use, while Version Catalogs provide a
centralized, type-safe mechanism to declare and manage all project dependencies and plugin versions.

By using Version Catalogs, your convention plugins can reference dependencies by their alias (e.g.,
libs.androidx.coreKtx or libs.plugins.androidApplication) instead of hardcoding strings and
versions. This ensures:

- **Type-Safe Access:** Compile-time checking for dependency aliases prevents typos and provides IDE
  auto-completion.

- **Centralized Version Management:** All dependency versions are managed in a single .toml file
  (e.g., libs.versions.toml), making updates simple and consistent across the entire project,
  including within your convention plugins.

- **Improved Readability:** Build scripts become cleaner and more declarative.

## Module Structure

The `build-logic` module's structure is as follows:

```
build-logic/
├── ...
├── convention/            # Source code for convention plugins
│   ├── ...
│   ├── src/
│   │   └── main/
│   │       └── kotlin/
│   │           └── com.example.hnote/
│   │           ├   ├── AndroidCompose.kt
│   │           ├   ├── GradleManagedDevices.kt
│   │           ├   ├── KotlinAndroid.kt
│   │           ├   └── ProjectExtensions.kt
│   │           ├── AndroidApplicationComposeConventionPlugin.kt
│   │           ├── AndroidApplicationConventionPlugin.kt
│   │           ├── AndroidFeatureConventionPlugin.kt
│   │           ├── AndroidLibraryComposeConventionPlugin.kt
│   │           ├── AndroidLibraryConventionPlugin.kt
│   │           ├── AndroidRoomConventionPlugin.kt
│   │           ├── AndroidTestConventionPlugin.kt
│   │           ├── HiltConventionPlugin.kt
│   │           └── JvmLibraryConventionPlugin.kt
│   └── build.gradle.kts   # Build file for the 'convention' sub-module
└── settings.gradle.kts
```

The core logic resides within `build-logic/convention/src/main/kotlin/com.example.hnote/`.
Each .kt file within this directory typically defines a specific convention plugin.

## How Convention Plugins Work

Each Kotlin file in the convention source set (e.g., HiltConventionPlugin.kt) implements Gradle's
Plugin<Project> interface. Inside the apply(target: Project) method, the plugin configures the
Project object (which represents a consuming module). This configuration can include:

Applying other standard Gradle or third-party plugins.

- Configuring Android extensions (e.g., compileSdk, minSdk, targetSdk, buildFeatures,
  composeOptions).
- Adding common dependencies, often referencing aliases defined in your Version Catalog.
- Setting up build types or product flavors.
- Defining compiler options.

### Plugin Registration

The following code snippet, typically found in `build-logic/convention/build.gradle.kts`, is
responsible for registering all the custom convention plugins defined within this module. This
registration makes them discoverable and applicable by other modules in your multi-module project
using their defined id.

```
// build-logic/convention/build.gradle.kts

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.hnote.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.hnote.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        ...
    }
}

```

Example: `AndroidApplicationConventionPlugin.kt` (Conceptual with Version Catalog usage)

```
// build-logic/convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt

import ...

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("com.android.application").get().get().pluginId)
            apply(plugin = libs.findPlugin("org.jetbrains.kotlin.android").get().get().pluginId)

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = 36
                ...
            }
        }
    }
}
```

## Application in Other Modules

Once built, these convention plugins become available to other modules in your project. for example,
would simply apply the convention plugin in its `build.gradle.kts`file:

```
// app/build.gradle.kts

plugins {
    alias(libs.plugins.hnote.android.application)
    ...
}

...
```

Building and Usage
The `build-logic` module is typically built automatically by Gradle when other modules depend on its
plugins. You don't usually run direct build commands against `build-logic` itself in isolation. When
you execute a build task from the root of your project (e.g., `./gradlew assembleDebug`), Gradle
first compiles the convention plugins in `build-logic` and then makes them available for application
to your other modules.

This setup significantly streamlines the development process for complex applications, ensuring a
robust and easily scalable build system.