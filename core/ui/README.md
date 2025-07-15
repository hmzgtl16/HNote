# `:core:ui` Module

The `:core:ui` module is a dedicated component within the application responsible for housing
reusable UI components that are specific to the application's domain but are not part of the core
design system. These components often combine elements from the `:core:design` module with
application-specific data models to create more complex, self-contained UI building blocks.

## Purpose and Importance

This module plays a crucial role in building the application's user interface by:

- **Encapsulating Domain-Specific UI:** It contains UI components that are tailored to the
  application's unique features (e.g., a `Note Card` or `Reminder Card`), unlike the generic
  components found in `:core:design`.

- **Promoting Reusability:** These components can be reused across different feature modules that
  display similar types of data, ensuring consistency in presentation.

- **Bridging Design and Data:** It acts as an intermediary, taking data models (from `:core:model`)
  and rendering them using the design system's primitives (from `:core:design`).

- **Simplifying Feature Development:** Feature modules can compose these higher-level UI components
  instead of building every UI element from scratch, accelerating development.

- **Facilitating Previews:** Includes extensive preview providers to easily visualize and test UI
  components in various states and with different data.

## Module Structure

The `:core:ui` module's structure is as follows:

```
ui/
├── build/
├── src
│   ├── ...
│   └── main
│       ├── kotlin/com/example/hnotes/core/ui
│       │   ├── ItemCard.kt                            # UI component for displaying an item
│       │   ├── NoteCard.kt                            # UI component for displaying a note
│       │   ├── ReminderCard.kt                        # UI component for displaying a reminder
│       │   └── ...
│       └── ...
├── build.gradle.kts
└── ...
```

## Key Components:

- **Domain-Specific UI Components**
- **Preview Providers**
- **Utilities**

## Usage

Feature modules will primarily depend on `:core:ui` to construct their screens. Instead of building
a SearchQueryCard from scratch in every feature that displays a note, they can simply use the
`SearchQueryCard`Composable provided by this module.

```
// In RecentSearches Composable function within a :feature:search module

@Composable
fun RecentSearches(
    queries: List<SearchQuery>,
    onRecentSearchClicked: (SearchQuery) -> Unit,
    onClearRecentSearch: (SearchQuery) -> Unit,
    ...
) {
    LazyColumn(
        ...
        content = {
            ...
            items(
                items = queries,
                key = { it.hashCode() },
                contentType = { it },
                itemContent = {
                    SearchQueryCard(
                        searchQuery = it,
                        onClick = onRecentSearchClicked,
                        onClear = onClearRecentSearch,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    )
}
```

