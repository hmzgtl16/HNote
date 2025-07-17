# `:core:data` Module

```mermaid
%%{
  init: {
    'theme': 'dark'
  }
}%%

graph LR
  subgraph :core
    :core:data["data"]
    :core:database["database"]
    :core:datastore["datastore"]
    :core:alarm["alarm"]
  end

  :core:data --> :core:database
  :core:data --> :core:datastore
  :core:data --> :core:alarm
  classDef android-library fill: #3BD482, stroke: #fff, stroke-width: 2px, color: #fff;
  classDef unknown fill: #676767, stroke: #fff, stroke-width: 2px, color: #fff;
  class :core:data android-library
  class :core:database unknown
  class :core:datastore unknown
  class :core:alarm unknown

```
##  
___

The `:core:data` module serves as the application's repository layer, acting as a single source of
truth for all data operations. It abstracts the underlying data sources (such as local databases,
DataStore) and provides a clean, consistent API for the rest of the application to access and
manipulate data.

The `:core:data` module serves as the application's repository layer, acting as a single source of
truth for all data operations. It abstracts the underlying data sources (such as local databases,
DataStore) and provides a clean, consistent API for the rest of the application to access and
manipulate data.

## Purpose and Importance

This module is crucial for implementing the Repository pattern, which decouples the application's
business logic from the specific data sources. Its primary responsibilities include:

- **Data Abstraction:** Hides the complexities of data fetching, caching, and synchronization from
  the UI and business logic layers
- **Single Source of Truth:** Provides a unified API for data, ensuring that all data consumers get
  consistent information, regardless of where the data originates (e.g., from the network or local
  database)
- **Offline Support:** Can implement caching strategies to provide data even when the device is
  offline
- **Testability:** Makes it easier to test business logic independently of actual data sources by
  providing mockable repository interfaces
- **Modularity:** Centralizes data handling, making the codebase more organized and easier to
  maintain

## Module Structure

```
data/
├── ...
├── src/
│   ├── main/kotlin/com/example/hnotes/core/data/
│   │   ├── di/
│   │   ├── repository/
│   │   │   ├── NoteRepository.kt         
│   │   │   ├── NoteRepositoryImpl.kt    
│   │   │   ├── SearchRepository.kt       
│   │   │   ├── SearchRepositoryImpl.kt   
│   │   │   ├── UserDataRepository.kt    
│   │   │   └── UserDataRepositoryImpl.kt 
│   │   ├── util/
│   │   │   └── Mapper.kt                 # Utility for mapping between data layers
│   │   └── ...
│   └── ...
├── build.gradle.kts
└── ...
```

## Key Components

### Dependency Injection

**`di/DataModule.kt`:** This Dagger Hilt module is responsible for providing the concrete
implementations of the repository interfaces (e.g., `NoteRepositoryImpl` as `NoteRepository`) to the
rest of the application via dependency injection.

### Repository Layer

The `repository/` directory contains the interfaces and their implementations for various data
domains. This is where the core logic of fetching, combining, and caching data resides.

#### Note Management

- **`NoteRepository.kt`:** Interface for Note data operations
- **`NoteRepositoryImpl.kt`:** Implementation of `NoteRepository` - handles all data operations
  related to notes (e.g., fetching, saving, deleting notes, potentially interacting with
  `:core:database` module's `NoteDao`)

#### Search Operations

- **`SearchRepository.kt`:** Interface for Search data operations
- **`SearchRepositoryImpl.kt`:** Implementation of `SearchRepository` - manages data operations for
  search functionalities (e.g., performing searches, storing search history, potentially using
  `:core:database` module's `SearchQueryDao`)

#### User Data Management

- **`UserDataRepository.kt`:** Interface for UserData operations
- **`UserDataRepositoryImpl.kt`:** Implementation of `UserDataRepository` - deals with user-specific
  data and preferences (e.g., fetching from `:core:datastore` module, potentially syncing with a
  remote server)

### Utilities

**`util/Mapper.kt`:** This utility class (or set of extension functions) is used for mapping data
between different layers. For example, it might convert Room entities (from `:core:database`) or
network DTOs into the core data models defined in `:core:model`, and vice-versa. This ensures that
the repository always exposes data in the application's canonical model format.

## Technologies Used

- **Kotlin:** The primary programming language
- **Kotlin Coroutines & Flow:** Used extensively for asynchronous data operations and for exposing
  streams of data (e.g., `Flow<List<Note>>`) that can be observed by higher layers
- **Dagger Hilt:** For dependency injection, making repository instances easily injectable into
  `ViewModel`s or other business logic components
- **Jetpack Room:** (Indirectly, via dependency on `:core:database`) For local data persistence
- **Jetpack DataStore:** (Indirectly, via dependency on `:core:datastore`) For managing user
  preferences and configuration data
- **Serialization Libraries:** (e.g., kotlinx.serialization) (Potentially, if handling data) For
  converting JSON to Kotlin objects

## Usage

Other modules, particularly `feature` modules and `ViewModel`s, will depend on the interfaces
defined in this module (e.g., `NoteRepository`, `UserDataRepository`) to access and manipulate
application data.
They should not directly interact with the underlying data sources (database, DataStore).

### Example Usage in a ViewModel

```
// In NoteViewModel in a :feature:note module

import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.Note

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository.
    ...
) : ViewModel() {
    fun deleteNote(note: Note) = viewModelScope.launch {
        ...
        noteRepository.deleteNote(note)
        ...
    }
}
```