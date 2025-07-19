# `:feature:notes` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%
graph LR
    subgraph :feature
        :feature:notes["notes"]
    end
    subgraph :core
        :core:data["data"]
        :core:ui["ui"]
        :core:navigation["navigation"]
    end

    :feature:notes --> :core:data
    :feature:notes --> :core:ui
    :feature:notes --> :core:navigation
    classDef android-library fill: #3BD482, stroke: #fff, stroke-width: 2px, color: #fff;
    classDef unknown fill: #676767, stroke: #fff, stroke-width: 2px, color: #fff;
    class :feature:notes android-library
    class :core:data unknown
    class :core:ui unknown
    class :core:navigation unknown

```

---

##   

This module contains the main notes list functionality of the HNotes application. It provides the UI
and business logic for displaying, managing, and organizing all notes in the application.

## Features

- Display list of all notes
- Note preview cards
- Empty state handling
- Note deletion and archival
- Quick actions on notes (copy, delete, etc.)

## Screenshots

![Empty State](../../assets/screenshots/Empty%20Screen.png)
![Home Screen with Notes](../../assets/screenshots/Home%20Screen.png)

The notes module implements the main screen of the application, showing either an empty state when
no notes exist or a grid/list of note preview cards when notes are present. Users can interact with
individual notes through this interface to view, edit, or perform quick actions.
