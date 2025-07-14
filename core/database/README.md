# `:core:database` Module

The `:core:database` module is a core component responsible for managing the application's local
data
persistence. It primarily leverages Room Persistence Library, part of Android Jetpack, to provide
an abstraction layer over SQLite, enabling robust and efficient database operations.

## Purpose and Importance

This module centralizes all local data storage logic, ensuring that the application's data is
managed consistently and safely. By using Room, it offers:

- **Compile-time SQL Validation:** Room validates your SQL queries at compile time, preventing
  runtime errors.
- **Boilerplate Reduction:** Generates much of the boilerplate code needed to interact with SQLite
  databases.
- **Observable Queries:** Integrates seamlessly with Kotlin Coroutines and Flow, allowing for
  reactive data observation.
- **Type Safety:** Ensures type-safe interactions with the , reducing the chance of casting errors.

This module is critical for storing structured application data, such as notes, items, reminders,
and search queries, enabling offline functionality and efficient data retrieval.

## Module Structure

The `:core:database` module's structure is as follows:

```
database/
├── ...
├── src
│   ├── main
│   │   ├── kotlin/com/example/hnote/core/database
│   │   │   ├── dao
│   │   │   │   ├── NoteDao.kt                 # Data Access Object for NoteEntity
│   │   │   │   ├── NoteFtsDao.kt              # Data Access Object for NoteFtsEntity (Full-Text Search)
│   │   │   │   └── SearchQueryDao.kt          # Data Access Object for SearchQueryEntity
│   │   │   ├── di
│   │   │   │   ├── DaoModule.kt               # Provides DAO dependencies for DI
│   │   │   │   └── DatabaseModule.kt          # Provides database instance for DI
│   │   │   ├── model
│   │   │   │   ├── ItemEntity.kt              # Room Entity for items
│   │   │   │   ├── ItemFtsEntity.kt           # Room Entity for item full-text search
│   │   │   │   ├── NoteEntity.kt              # Room Entity for notes
│   │   │   │   ├── NoteFtsEntity.kt           # Room Entity for note full-text search
│   │   │   │   ├── NoteWithItemsAndRemind.kt  # Data class for relationships (notes with items and reminders)
│   │   │   │   ├── ReminderEntity.kt          # Room Entity for reminders
│   │   │   │   └── SearchQueryEntity.kt       # Room Entity for search queries
│   │   │   ├── util
│   │   │   │   ├── InstantConverter.kt        # Type converter for java.time.Instant
│   │   │   │   ├── ItemCallback.kt            # DiffUtil.ItemCallback for ItemEntity
│   │   │   │   ├── NoteCallback.kt            # DiffUtil.ItemCallback for NoteEntity
│   │   │   │   ├── NoteType.kt                # Enum or sealed class for note types
│   │   │   │   ├── NoteTypeConverter.kt       # Type converter for NoteType
│   │   │   │   ├── ReminderRepeateMode.kt     # Enum or sealed class for reminder repeat modes
│   │   │   │   └── ReminderRepeateModeConverter.kt # Type converter for ReminderRepeateMode
│   │   │   └── ApplicationDatabase.kt         # Room database class
│   │   └── ...
│   └── test
│       └── java/com/example/hnote/core/database
│           └── ...
├── build.gradle.kts
└── ...
```

### Key Components:

- `dao/`: Contains Data Access Objects (DAOs). These are interfaces that define the methods for
  interacting with the database (e.g., insert, update, delete, query).
    - `NoteDao.kt`: Handles operations related to `NoteEntity`.
    - `NoteFtsDao.kt`: Likely for full-text search (FTS) capabilities on notes.
    - `SearchQueryDao.kt`: Manages operations for storing and retrieving search queries.

- `di/`: Houses Dagger Hilt modules for dependency injection.
    - `DaoModule.kt`: Provides instances of the various DAOs.
    - `DatabaseModule.kt`: Provides the singleton instance of `ApplicationDatabase`.

- `model/`: Defines the database schema through Room Entity classes and data classes for
  relationships.
    - `*Entity.kt`: Classes annotated with @Entity that represent tables in the SQLite database.
    - `NoteWithItemsAndRemind.kt`: A data class used to represent a complex relationship, likely
      combining data from `NoteEntity`, `ItemEntity`, and `ReminderEntity` through Room's
      `@Relation` or `@Embedded` annotations.

- `util/`: Contains utility classes and type converters for Room.
    - `*Converter.kt`: Classes annotated with @TypeConverter that tell Room how to convert custom
      data types (like `Instant`, `RepeatMode`) into types that can be stored in the database.

- `ApplicationDatabase.kt`: The main Room database class, annotated with `@Database`. It defines the
  entities, version, and provides abstract methods to access the DAOs.

## Technologies Used

- **Room Persistence Library:** The primary ORM (Object-Relational Mapping) library for SQLite
  database
  interactions.
- **SQLite:** The underlying database technology.
- **Kotlin:** The programming language used for all components.
- **Kotlin Coroutines & Flow:** Used for asynchronous database operations and observing data changes
  reactively.
- **Dagger Hilt:** (Implied by `di/` package) For dependency injection, ensuring
  `ApplicationDatabase` and
  DAOs are easily injectable and testable.

## Usage

Other modules (e.g., data module) that need to interact with the local database will inject the
necessary DAOs or a repository that wraps these DAOs.

### Example (Conceptual - in another module):

```
// In a SearchRepository in data module

import ...

class SearchRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val searchQueryDao: SearchQueryDao,
    private val noteFtsDao: NoteFtsDao
) : SearchRepository {
    override suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult> {
        val noteIds = noteFtsDao.searchAllNotes(text = "*${searchQuery.query}*")
        val noteIdsWithItems = noteFtsDao.searchAllItems(text = "*${searchQuery.query}*")

        val noteFlow = combine(
            flow = noteIds,
            flow2 = noteIdsWithItems
        ) { ids, idsWithItems -> (ids + idsWithItems).toSet() }
            .distinctUntilChanged()
            .flatMapLatest(noteDao::getNotesByIds)

        return noteFlow
            .map { it.map(NoteWithItemsAndReminder::toModel) }
            .map(::SearchResult)
    }

    override fun getAllSearchQueries(limit: Int): Flow<List<SearchQuery>> =
        searchQueryDao.getSearchQueries(limit = limit)
            .map { it.map(SearchQueryEntity::toModel) }

    override suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery) =
        searchQueryDao.upsertSearchQuery(searchQuery = searchQuery.toEntity())

    override suspend fun delete(searchQuery: SearchQuery) =
        searchQueryDao.delete(searchQuery = searchQuery.toEntity())

    override suspend fun deleteAll() =
        searchQueryDao.deleteAll()
}
```

## Building and Testing

This module is built as part of the overall Gradle project. Its tests (located in`src/androidTest`)
ensure the correct functioning of database operations, including entity mappings, DAO queries.