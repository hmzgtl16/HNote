# HNote

HNote is a modular Android application designed for note-taking, reminders, and productivity
enhancements. The project leverages a modern multi-module architecture, utilizing Gradle convention
plugins and version catalogs to ensure maintainability, scalability, and consistency across all
modules.

---

## Project Structure

The project is organized into several key modules:

- **app/**: The main application module.
- **core/**: Contains core libraries and utilities (e.g., alarm, common, data, database, datastore,
  design, model, navigation, notification, ui).
- **feature/**: Feature-specific modules (e.g., note, notes, search, settings).
- **benchmark/**: Benchmarking and performance testing.
- **build-logic/**: Hosts custom Gradle convention plugins for centralized build logic.
- **gradle/**: Gradle wrapper and version catalog configuration.

---

## Build Logic and Convention Plugins

The project uses a dedicated `build-logic` module to define and manage Gradle convention plugins.
These plugins encapsulate common build configurations, reducing boilerplate and ensuring consistency
across all modules. For more details, see [`build-logic/README.md`](build-logic/README.md).

The project also utilizes Gradle Version Catalogs (see `gradle/libs.versions.toml`) for centralized
dependency and plugin version management.

---

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone <repository-url>
   cd HNote
   ```
2. **Open in Android Studio** (recommended) or use the command line.
3. **Build the project:**
   ```sh
   ./gradlew assembleDebug
   ```

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

