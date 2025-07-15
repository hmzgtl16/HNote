# `:core:design` Module

The `:core:design` module is a dedicated component for housing the application's design system,
including reusable UI components, typography, color palettes, and theming definitions. It
centralizes all visual elements to ensure a consistent and cohesive user experience across the
entire application.

## Purpose and Importance

This module is fundamental for establishing and maintaining a consistent visual identity for the
application. By centralizing design elements, it offers several key advantages:

- **Design Consistency:** Ensures that all UI elements adhere to a unified design language,
  providing a seamless user experience.

- **Faster UI Development:** Developers can reuse pre-built, styled components, significantly
  speeding up UI implementation.

- **Reduced Duplication:** Prevents repeated definitions of colors, fonts, and common UI patterns
  across different feature modules.

- **Easier Theming:** Simplifies the process of implementing and switching between different
  themes (e.g., light/dark mode) by centralizing theme definitions.

- **Improved Maintainability:** Changes to the design system can be made in one place and propagated
  throughout the application, reducing the effort required for design updates.

- **Accessibility:** Encourages the implementation of accessibility best practices within common
  components.

## Module Structure

The `:core:design` module's structure is as follows:

```
design/
├── ...
├── src
│   ├── ...
│   └── main
│       ├── kotlin/com/example/hnote/core/design
│       │   ├── component                  # Reusable UI components (e.g., buttons, text fields)
│       │   │   ├── AlertDialog.kt
│       │   │   ├── Background.kt
│       │   │   ├── BottomAppBar.kt
│       │   │   └── ...
│       │   ├── icon                       # Application icons
│       │   │   └── AppIcons.kt
│       │   └── theme                      # Theming definitions (colors, typography, shapes)
│       │       ├── Background.kt
│       │       ├── Color.kt
│       │       ├── Gradient.kt
│       │       └── ...
│       └── AndroidManifest.xml
├── build.gradle.kts
└── ...
```

## Key Components:

- `component/`: This directory contains individual, reusable Jetpack Compose UI components. These
  components are built with the application's specific design tokens (colors, typography, shapes)
  and often encapsulate common UI patterns. Examples include `Button.kt`, `TextField.kt`,
  `AlertDialog.kt`, `BottomAppBar.kt`, `TopAppBar.kt`, `SearchBar.kt`, etc.

- `icon/AppIcons.kt`: This file probably defines a centralized object or set of constants for all
  application-specific icons, ensuring consistent usage and easy updates.

- theme/: This directory defines the core elements of the application's theme using Jetpack Compose
  theming APIs. Examples include `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`, etc.

## Technologies Used

- **Jetpack Compose:** The modern Android UI toolkit used for building declarative UIs and the
  foundation for all components within this module.

## Usage

Other UI-related modules (e.g., `:feature:note` module) will depend on :core:design to leverage
its predefined UI components, themes, and icons. This allows developers to focus on feature-specific
UI logic rather than low-level styling.

Example (Conceptual - in a `:feature:note` module Composable):

```
// In NoteScreen Composable function within a :feature:note module

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteScreen(
    uiState: NoteUiState,
    onEvent: (NoteScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    ...
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = uiState.backgroundColor,
        bottomBar = {
            AppBottomAppBar(
                actions = {
                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.Undo) },
                        enabled = uiState.canUndo,
                        icon = {
                            Icon(
                                imageVector = AppIcons.Undo,
                                contentDescription = null,
                            )
                        }
                    )
                    ...
                }
                ...
            )
        },
        content = { 
          ...
        }
    )
    ...
}
```