# `:feature:note` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :feature
    :feature:note["note"]
  end
  subgraph :core
    :core:data["data"]
    :core:ui["ui"]
    :core:navigation["navigation"]
  end

  :feature:note --> :core:data
  :feature:note --> :core:ui
  :feature:note --> :core:navigation

classDef android-library fill:#3BD482,stroke:#fff,stroke-width:2px,color:#fff;
classDef unknown fill:#676767,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:note android-library
class :core:data unknown
class :core:ui unknown
class :core:navigation unknown

```

##

This module implements the note editing and viewing functionality for the HNotes application. It
provides a rich text editor interface for creating and modifying individual notes.

## Features

- Rich text editor with formatting options
- Note title and content editing
- Auto-save functionality
- Share note functionality
- Delete note option
- Last modified timestamp
- Category/label assignment
- Color selection for notes
- Pin/unpin note option

## Dependencies

## Screenshots

The note module provides a clean, focused interface for note creation and editing. Screenshots will
be available once the note editor UI is finalized.

To add screenshots:

1. Capture the note editor screen in both light and dark themes
2. Save them in the `assets/screenshots` directory as:
    - `Note_Editor_Light.png`
    - `Note_Editor_Dark.png`

The note editor interface uses Material Design components and follows the app's design system for a
consistent user experience. It provides a distraction-free environment for note-taking while
maintaining quick access to formatting tools and note options.