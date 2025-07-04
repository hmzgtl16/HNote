package com.example.hnote.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.util.NoteType
import com.example.hnote.core.database.util.ReminderRepeatMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class NoteDaoTest {

    private lateinit var db: ApplicationDatabase
    private lateinit var noteDao: NoteDao

    @BeforeTest
    fun setup() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                ApplicationDatabase::class.java,
            ).build()
        }
        noteDao = db.noteDao()
    }

    @AfterTest
    fun teardown() = db.close()

    @Test
    fun upsertNoteNewNoteInsertion() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        val actual = noteDao.upsertNote(note)
        assertNotEquals(0L, actual)
    }

    @Test
    fun upsertNoteExistingNoteUpdate() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        val initialId = noteDao.upsertNote(note)
        val updatedNote = note.copy(
            id = initialId,
            title = "Updated Test Note",
            content = "This is an updated test note.",
            updatedAt = Instant.parse("2023-10-02T10:00:00Z")
        )
        val updatedId = noteDao.upsertNote(updatedNote)

        assertEquals(expected = -1L, actual = updatedId)
    }

    @Test
    fun upsertNoteWithItemsNewNoteInsertionWithEmptyItemsList() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        val items = emptyList<ItemEntity>()

        noteDao.upsertNoteWithItems(note = note, items = items)

        val noteWithItems = noteDao.getAllNotes().first()

        assertEquals(expected = 1, actual = noteWithItems.size)
        assertEquals(expected = note.title, actual = noteWithItems.first().note.title)
        assertEquals(expected = 0, actual = noteWithItems.first().items.size)
    }

    @Test
    fun upsertNoteWithItemsNewNoteInsertionWithItemsList() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        val items = listOf(
            testItemEntity(content = "Item 1"),
            testItemEntity(content = "Item 2")
        )

        noteDao.upsertNoteWithItems(note = note, items = items)

        val noteWithItems = noteDao.getAllNotes().first()

        assertEquals(expected = 1, actual = noteWithItems.size)
        assertEquals(expected = note.title, actual = noteWithItems.first().note.title)
        assertEquals(expected = 2, actual = noteWithItems.first().items.size)
    }

    @Test
    fun upsertNoteWithItemsExistingNoteUpdateWithEmptyItemsList() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        val initialId = noteDao.upsertNote(note)
        val updatedNote = note.copy(
            id = initialId,
            title = "Updated Test Note",
            content = "This is an updated test note.",
            updatedAt = Instant.parse("2023-10-02T10:00:00Z")
        )
        val items = emptyList<ItemEntity>()

        noteDao.upsertNoteWithItems(note = updatedNote, items = items)

        val noteWithItems = noteDao.getAllNotes().first()

        assertEquals(expected = 1, actual = noteWithItems.size)
        assertEquals(expected = updatedNote.title, actual = noteWithItems.first().note.title)
        assertEquals(expected = 0, actual = noteWithItems.first().items.size)
    }

    @Test
    fun upsertNoteWithItemsExistingNoteUpdateWithItemsList() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        val initialId = noteDao.upsertNote(note)
        val updatedNote = note.copy(
            id = initialId,
            title = "Updated Test Note",
            content = "This is an updated test note.",
            updatedAt = Instant.parse("2023-10-02T10:00:00Z")
        )
        val items = listOf(
            testItemEntity(content = "Updated Item 1"),
            testItemEntity(content = "Updated Item 2")
        )

        noteDao.upsertNoteWithItems(note = updatedNote, items = items)

        val noteWithItems = noteDao.getAllNotes().first()

        assertEquals(expected = 1, actual = noteWithItems.size)
        assertEquals(expected = updatedNote.title, actual = noteWithItems.first().note.title)
        assertEquals(expected = 2, actual = noteWithItems.first().items.size)
    }

    @Test
    fun deleteNote() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )

        noteDao.upsertNoteWithItems(note = note, items = emptyList())

        val notes = noteDao.getAllNotes().first()

        noteDao.deleteNote(note = notes.first().note)

        val deletedNote = noteDao.getNoteById(id = notes.first().note.id)

        assertNull(actual = deletedNote)
    }

    @Test
    fun deleteNotes() = runTest {
        val note1 = testNoteEntity(
            title = "Test Note 1",
            content = "This is the first test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )
        val note2 = testNoteEntity(
            title = "Test Note 2",
            content = "This is the second test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-02T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-02T10:00:00Z")
        )
        val note3 = testNoteEntity(
            title = "Test Note 3",
            content = "This is the third test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-03T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-03T10:00:00Z")
        )

        noteDao.upsertNoteWithItems(note = note1, items = emptyList())
        noteDao.upsertNoteWithItems(note = note2, items = emptyList())
        noteDao.upsertNoteWithItems(note = note3, items = emptyList())

        val notes = noteDao.getAllNotes().first()

        noteDao.deleteNotes(notes = listOf(notes.first().note, notes.last().note))

        val remainingNotes = noteDao.getAllNotes().first()

        assertEquals(expected = 1, actual = remainingNotes.size)
        assertEquals(expected = note2.title, actual = remainingNotes.first().note.title)
    }

    @Test
    fun deleteItem() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )
        val items = listOf(
            testItemEntity(content = "Item 1"),
            testItemEntity(content = "Item 2"),
            testItemEntity(content = "Item 3")
        )

        noteDao.upsertNoteWithItems(note = note, items = items)

        val notes = noteDao.getAllNotes().first()

        noteDao.deleteItem(item = notes.first().items.first())

        val notesAfterDeletion = noteDao.getAllNotes().first()

        assertEquals(expected = 2, actual = notesAfterDeletion.first().items.size)
    }

    @Test
    fun deleteItems() = runTest {
        val note = testNoteEntity(
            title = "Test Note",
            content = "This is a test note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )
        val items = listOf(
            testItemEntity(content = "Item 1"),
            testItemEntity(content = "Item 2"),
            testItemEntity(content = "Item 3")
        )

        noteDao.upsertNoteWithItems(note = note, items = items)

        val notes = noteDao.getAllNotes().first()

        noteDao.deleteItems(items = notes.first().items)

        val notesAfterDeletion = noteDao.getAllNotes().first()

        assertEquals(expected = 0, actual = notesAfterDeletion.first().items.size)
    }

    private suspend fun insertNotes() {
        val noteEntities = listOf(
            testNoteEntity(
                id = 1L,
                title = "compose",
                content = "Compose is a modern toolkit for building native UI.",
                type = NoteType.SIMPLE,
                createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-01T10:00:00Z")
            ),
            testNoteEntity(
                id = 2L,
                title = "kotlin",
                content = "Kotlin is a modern programming language that makes developers happier.",
                type = NoteType.SIMPLE,
                createdAt = Instant.parse("2023-10-02T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-02T10:00:00Z")
            ),
            testNoteEntity(
                id = 3L,
                title = "android",
                content = "Android is a mobile operating system developed by Google.",
                type = NoteType.SIMPLE,
                createdAt = Instant.parse("2023-10-03T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-03T10:00:00Z")
            ),
            testNoteEntity(
                id = 4L,
                title = "flutter",
                content = "Flutter is an open-source UI software development toolkit created by Google.",
                type = NoteType.SIMPLE,
                createdAt = Instant.parse("2023-10-04T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-04T10:00:00Z")
            ),
            testNoteEntity(
                id = 5L,
                title = "Watch Movie",
                content = "Reminder to watch a movie at 3 PM.",
                type = NoteType.REMINDER,
                reminderMode = ReminderRepeatMode.NONE,
                isCompleted = false,
                reminder = Instant.parse("2023-10-05T15:00:00Z"),
                createdAt = Instant.parse("2023-10-05T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-05T10:00:00Z")
            ),
            testNoteEntity(
                id = 6L,
                title = "Wake Up",
                content = "Reminder to wake up at 7 AM.",
                type = NoteType.REMINDER,
                reminderMode = ReminderRepeatMode.DAILY,
                isCompleted = true,
                reminder = Instant.parse("2023-10-06T07:00:00Z"),
                createdAt = Instant.parse("2023-10-06T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-06T10:00:00Z")
            ),
            testNoteEntity(
                id = 7L,
                title = "Grocery List",
                content = "List of groceries to buy.",
                type = NoteType.CHECK_LIST,
                createdAt = Instant.parse("2023-10-07T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-07T10:00:00Z")
            ),
            testNoteEntity(
                id = 8L,
                title = "Meeting Notes",
                content = "Notes from the meeting with the team.",
                type = NoteType.CHECK_LIST,
                createdAt = Instant.parse("2023-10-08T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-08T10:00:00Z")
            ),
        )

        noteEntities.forEach {
            noteDao.upsertNote(note = it)
        }

        val itemEntities = listOf(
            testItemEntity(
                id = 1L,
                content = "Potato",
                isCompleted = false,
                noteId = 7L
            ),
            testItemEntity(
                id = 2L,
                content = "Milk",
                isCompleted = true,
                noteId = 7L
            ),
            testItemEntity(
                id = 3L,
                content = "Apples",
                isCompleted = false,
                noteId = 7L
            ),
            testItemEntity(
                id = 4L,
                content = "Chicken",
                isCompleted = true,
                noteId = 7L
            ),
            testItemEntity(
                id = 5L,
                content = "Agenda and Summary",
                isCompleted = false,
                noteId = 8L
            ),
            testItemEntity(
                id = 6L,
                content = "Questions",
                isCompleted = true,
                noteId = 8L
            )
        )

        noteDao.upsertItems(items = itemEntities)
    }
}

private fun testNoteEntity(
    id: Long = 0L,
    title: String,
    content: String,
    type: NoteType,
    reminder: Instant? = null,
    reminderMode: ReminderRepeatMode? = null,
    isCompleted: Boolean? = null,
    createdAt: Instant,
    updatedAt: Instant,
): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    type = type,
    reminder = reminder,
    reminderMode = reminderMode,
    completed = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun testItemEntity(
    id: Long = 0L,
    content: String,
    isCompleted: Boolean = false,
    noteId: Long = 0L
): ItemEntity = ItemEntity(
    id = id,
    content = content,
    checked = isCompleted,
    noteId = noteId
)