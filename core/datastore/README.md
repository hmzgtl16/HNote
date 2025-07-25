# `:core:datastore` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%
graph LR
  subgraph :core
    :core:datastore["datastore"]
    :core:common["common"]
    :core:model["model"]
  end

  :core:datastore --> :core:common
  :core:datastore --> :core:model
  classDef android-library fill: #3BD482, stroke: #fff, stroke-width: 2px, color: #fff;
  classDef unknown fill: #676767, stroke: #fff, stroke-width: 2px, color: #fff;
  class :core:datastore android-library
  class :core:common unknown
  class :core:model unknown

```

##   

___

The `:core:datastore` module is a core component responsible for managing application data
persistence,
specifically focusing on user preferences and UI theme configurations. It leverages Jetpack
DataStore, a modern and robust solution for data storage in Android applications.

## Purpose and Importance

This module provides a reliable and efficient way to store small, simple datasets and user
preferences. It moves beyond the limitations of SharedPreferences by offering:

- **Asynchronous API:** DataStore uses Kotlin Coroutines and Flow, ensuring that data operations are
  safe to call on the main thread.

- **Data Consistency:** Provides transactional API to update data, ensuring data integrity.

- **Type Safety:** Supports both Preferences DataStore (key-value pairs) and Proto DataStore (typed
  objects using Protocol Buffers), offering compile-time type safety.

This module centralizes all data storage logic, making it easy for other parts of the application to
interact with persisted data in a consistent and safe manner.

## Module Structure

The `:core:datastore` module's structure is as follows:

```
datastore/
├── ...
├── src
│   ├── main
│   │   ├── kotlin/com/example/hnotes/core/datastore
│   │   │   ├── di
│   │   │   │   └── DataStoreModule.kt         # Provides DataStore dependencies for DI
│   │   │   ├── PreferencesDataSource.kt       # Main class for accessing preferences
│   │   │   ├── UserPreferencesSerializer.kt   # Handles serialization/deserialization of preferences
│   │   │   └── Utils.kt                       # Utility functions for DataStore
│   │   ├── proto/com/example/hnotes/data
│   │   │   ├── ui_theme_config.proto          # Protocol Buffer schema for UI theme configuration
│   │   │   └── user_preferences.proto         # Protocol Buffer schema for user preferences
│   │   └── ...
│   └── test
│       └── ...
├── build.gradle.kts
└── ...
```

## Key Components:

- `di/DataStoreModule.kt`: This file likely contains Dagger Hilt modules that define how DataStore
  instances (e.g., `DataStore<Preferences>`, `DataStore<UserPreferences>`) and related dependencies
  are provided throughout the application.
- `PreferencesDataSource.kt`: This class serves as the primary interface for interacting with
  `Preferences DataStore`. It encapsulates the logic for reading and writing key-value pairs.
- `UserPreferencesSerializer.kt`: This class is crucial for `Proto DataStore`. It implements
  `Serializer<T>` (where T is `UserPreferences` from the `.proto` file) and defines how
  `UserPreferences`
  objects are serialized into and deserialized from a byte stream. This ensures type-safe and
  efficient storage of complex data structures.
- `Utils.kt`: Contains common utility functions or extensions related to DataStore operations.
- `proto/com/example/hnotes/data/ui_theme_config.proto`: Defines the schema for UI theme-related
  configurations using Protocol Buffers. This allows for structured and versionable storage of theme
  settings.
- `proto/com/example/hnotes/data/user_preferences.proto`: Defines the schema for general user
  preferences using Protocol Buffers. This enables storing complex user settings as a single,
  type-safe object.

## Technologies Used

- **Jetpack DataStore:** The primary library for asynchronous and consistent data storage.
    - **Proto DataStore:** For storing typed objects using Protocol Buffers.
- **Protocol Buffers (Protobuf):** A language-neutral, platform-neutral, extensible mechanism for
  serializing structured data. Used for defining the schemas in .proto files.
- **Kotlin Coroutines & Flow:** DataStore is built on these, providing asynchronous data operations
  and observing data changes.
- **Dagger Hilt:** (Implied by `di/DataStoreModule.kt`) For dependency injection, making DataStore
  instances easily injectable into other components.

## Usage

Other modules that need to read or write user preferences or UI theme configurations can inject the
appropriate DataStore instance (or a wrapper class like `PreferencesDataSource`) and use its API.

### Example (Conceptual - in another module):

```
// In a UserDataRepository in :core:data module


class UserDataRepositoryImpl @Inject constructor(
    private val userPreferenceSource: PreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData> = userPreferenceSource.userData

    override suspend fun setTheme(theme: Theme) {
        userPreferenceSource.setTheme(theme = theme)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferenceSource.setDynamicColor(useDynamicColor = useDynamicColor)
    }
}
```

## Building and Testing

This module is built as part of the overall Gradle project. Its tests (located in src/test) ensure
the correct functioning of data serialization, deserialization, and access logic.