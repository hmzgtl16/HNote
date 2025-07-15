# `:core:navigation `Module

The `:core:navigation` module is a crucial component responsible for managing in-app navigation
within the application. It provides a centralized, abstracted, and type-safe way to handle
navigation events, decoupling navigation logic from UI components and business logic.

## Purpose and Importance

This module encapsulates all navigation-related logic, ensuring a consistent and robust navigation
experience across the application. Its benefits include:

- **Centralized Navigation Logic:** All navigation events and their handling are defined in one
  place,
  simplifying maintenance and updates.

- **Decoupling:** Separates navigation concerns from specific UI implementations (e.g.,
  Composables),
  making components more reusable and testable.

- **Type Safety:** By using sealed classes or enums for NavigationEvent and Route, it promotes
  type-safe
  navigation, reducing runtime errors.

- **Testability:** Makes it easier to test navigation flows independently without relying on actual
  UI
  interactions.

- **Scalability:** Simplifies the process of adding new navigation destinations or modifying
  existing
  navigation flows as the application evolves.

## Module Structure

The `:core:navigation` module's structure is as follows:

```
navigation/
├── build/
├── src
│   ├── main
│   │   ├── kotlin/com/example/hnotes/core/navigation
│   │   │   ├── di
│   │   │   │   └── NavigationModule.kt  # Provides Navigation dependencies for DI
│   │   │   ├── NavigationEvent.kt       # Defines various navigation events
│   │   │   ├── Navigator.kt             # Interface for navigation operations
│   │   │   ├── NavigatorImpl.kt         # Implementation of the Navigator interface
│   │   │   └── Route.kt                 # Defines application routes/destinations
│   │   └── AndroidManifest.xml            # Module's Android Manifest
│   └── ...
├── build.gradle.kts
└── ...
```

## Key Components:

- `di/NavigationModule.kt`: This Dagger Hilt module is responsible for providing the necessary
  dependencies related to navigation, such as the `Navigator` implementation, ensuring it can be
  easily injected into other parts of the application.

- `NavigationEvent.kt`: This file likely defines a sealed class or an enum that represents various
  types of navigation actions that can occur in the application (e.g.,` NavigateToNote(id)`,
  `NavigateUp()`). This provides a structured way to express navigation intentions.

- `NavigatorImpl.kt`: This class provides the concrete implementation of the `Navigator` interface.
  It
  contains the actual logic for handling `NavigationEvent`s and performing the corresponding
  navigation actions using the chosen navigation framework.

- `Route.kt`: This file probably defines a sealed class or an enum that represents the various
  routes or destinations within the application's navigation graph. This provides a type-safe way to
  refer to specific screens.

## Technologies Used

- **Kotlin:** The primary programming language used for all components.

- **Jetpack Navigation Component (Compose Navigation):** (Implied by the module's purpose) The
  underlying framework for managing navigation graphs and transitions.

- **Dagger Hilt:** For dependency injection, making `Navigator` instances easily injectable into
  other
  components.

- **Kotlin Coroutines & Flow:** May be used for observing navigation events or handling asynchronous
  navigation logic.

- **Kotlin Serialization:**

## Usage

Other modules that need to trigger navigation will inject the `Navigator` interface and call its
methods with appropriate `NavigationEvent`s. This decouples the navigation logic from the calling
module, making the codebase cleaner and more maintainable.

Example (Conceptual - in another module):

```
// In NoteViewModel in :feature:note module

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val navigator: Navigator,
    ...
) : ViewModel() {
    private fun saveNote() = viewModelScope.launch {
        ...
        navigator.navigateBack()
    }
}

In App in :app module

@Composable
fun App(
    appState: AppState,
    ...
) {
    ...
    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner
            .repeatOnLifecycle(
                state = Lifecycle.State.STARTED,
                block = {
                    appState.navigator.events.collect {
                        when (it) {
                            is NavigationEvent.NavigateTo -> {
                                appState.navController.navigate(
                                    route = it.route,
                                    navOptions = it.navOptions
                                )
                            }

                            is NavigationEvent.NavigateBack -> {
                                appState.navController.navigateUp()
                            }
                        }
                    }
                }
            )
    }
    ...
}
```