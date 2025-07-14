# `:core:model` Module

The `:core:model` module is a fundamental component of the application that serves as the central
repository for all data models (entities) representing the application's core business objects. It
defines the structure and types of data that flow through various layers of the application,
ensuring consistency and type safety.

## Purpose and Importance

This module is designed to hold plain data classes that represent the "what" of your application's
data. Its primary responsibilities include:

- **Centralized Data Definitions:** Provides a single, canonical source for all data structures used
  across the application, preventing discrepancies and promoting consistency.

- **Type Safety:** Defines data models using Kotlin data classes, enums, or sealed classes, ensuring
  that data is handled with strong type guarantees at compile time.

- **Decoupling:** Keeps data model definitions separate from specific implementation details of data
  sources (e.g., database, network) or UI components. This allows for changes in data sources or UI
  without affecting the core data models.

- **Readability and Maintainability:** Simplifies understanding of the application's data landscape,
  making the codebase easier to navigate and maintain.

- **Cross-Module Communication:** Facilitates clear and consistent data exchange between different
  modules (e.g., feature modules, data modules, database module, datastore module).

## Module Structure

```
model/
├── build/
├── src
│   ├── main/kotlin/com/example/hnote/core/model
│   │   ├── Item.kt                 # Data class for individual items
│   │   ├── Note.kt                 # Data class for notes
│   │   ├── NoteType.kt             # Enum/sealed class for different note types
│   │   ├── Reminder.kt             # Data class for reminders
│   │   ├── RepeatMode.kt           # Enum class for reminder repeat modes
│   │   ├── SearchQuery.kt          # Data class for search queries
│   │   ├── SearchResult.kt         # Data class for search results
│   │   ├── Theme.kt                # Data class/enum for UI themes
│   │   └── UserData.kt             # Data class for user-specific data/preferences
├── build.gradle.kts
└── ...
```

## Usage

Any module that needs to represent or manipulate the core data of the application will import and
use the classes defined in ``:core:model``.