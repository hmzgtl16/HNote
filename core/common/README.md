# `:core:common` Module

The `:core:common` module serves as a foundational component within the project, encapsulating
widely used utilities, shared constants, dependency injection configurations for application-wide
scopes, and common abstractions.
Its purpose is to promote code reusability, reduce duplication, and establish a consistent base for
other modules.

## Purpose and Importance

This module is designed to hold elements that are frequently needed across various parts of the
application, regardless of their specific feature or domain. By centralizing these common
components,
it achieves:

- **Code Reusability:** Prevents the same code from being written multiple times in different
  modules.
- **Consistency:** Ensures that common patterns, utilities, and configurations are applied uniformly
  throughout the project.
- **Reduced Duplication:** Minimizes boilerplate and makes the codebase leaner and easier to
  maintain.
- **Clear Separation of Concerns:** Isolates generic, cross-cutting concerns from specific feature
  implementations.
- **Centralized Configuration:** Provides a single place for defining application-level scopes and
  dispatchers for coroutines.

## Module Structure

The `:core:common` module's structure is as follows:

```
common/
├── ...
├── src
│   ├── main
│   │   └── kotlin/com/example/hnote/core/common
│   │       ├── di
│   │       │   ├── ApplicationScopeModule.kt  # Provides CoroutineScope dependencies
│   │       │   └── DispatchersModule.kt       # Provides CoroutineDispatchers
│   │       ├── ApplicationDispatcher.kt       # Defines custom Coroutine Dispatchers
│   │       └── ApplicationScope               # Defines the main Application Coroutine Scope
│   └── test
│       └── ...
├── build.gradle.kts
└── ...
```

### Key Components:

- `di/`: Contains Dagger Hilt modules for application-wide dependency injection.
    - `ApplicationScopeModule.kt`: This module likely provides dependencies that need to live for
      the entire lifecycle of the application, typically bound to `@ApplicationScope`.
    - `DispatchersModule.kt`: This module provides instances of `CoroutineDispatcher` (e.g.,
      `Dispatchers.Default`, `Dispatchers.IO`) to ensure consistent and testable threading for
      coroutine operations across the app.
- `ApplicationDispatcher.kt`: This file probably defines custom annotation or qualifier for specific
  `CoroutineDispatcher` instance, allowing for clear distinction and injection of different
  dispatchers (e.g., `@ApplicationDispatcher`).
- `ApplicationScope.kt`: This likely defines a custom `CoroutineScope` (e.g., annotated with
  `@ApplicationScope`) that is tied to the application's lifecycle. This scope is used for
  long-running coroutines that should persist as long as the application is alive.

## Technologies Used

- **Kotlin:** The primary programming language used for all components.
- **Dagger Hilt:** For dependency injection, particularly for providing
  application-scoped instances and CoroutineDispatchers.
- **Kotlin Coroutines:** Utilized for asynchronous programming, with specific `CoroutineDispatchers`
  and `CoroutineScopes` defined for application-wide use.

## Usage

Other modules that require application-wide singletons, specific CoroutineDispatchers, or the main
ApplicationScope for long-running tasks will inject these components from the :core:common module.

### Example (Conceptual - in another module):

```
// In DataStoreModule in :core:datastore module

import com.example.hnote.core.common.ApplicationDispatcher
import com.example.hnote.core.common.ApplicationScope
import com.example.hnote.core.common.Dispatcher
import ...

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @ApplicationDispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory
        .create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + dispatcher),
            produceFile = { context.dataStoreFile(fileName = "user_preferences.pb") }
        )
}
```