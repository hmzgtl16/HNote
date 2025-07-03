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
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NoteDaoTest {

    private lateinit var db: ApplicationDatabase
    private lateinit var noteDao: NoteDao

    @Before
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

    @After
    fun teardown() = db.close()

    @Test
    fun insertNote_newEntryIsIgnoredIfAlreadyExists() = runTest {
        insertNotes()

        noteDao.insertNote(
            note = testNoteEntity(
                id = 1L,
                title = "compose",
                content = "Compose is a modern toolkit for building native UI.",
                type = NoteType.SIMPLE,
                createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-01T10:00:00Z")
            )
        )

        val savedNotes = noteDao.getAllNotes().first()

        assertEquals(expected = 8, actual = savedNotes.size)
    }

    @Test
    fun insertNote_newEntryIsInserted() = runTest {
        insertNotes()

        noteDao.insertNote(
            note = testNoteEntity(
                id = 9L,
                title = "New Note",
                content = "This is a new note.",
                type = NoteType.SIMPLE,
                createdAt = Instant.parse("2023-10-09T10:00:00Z"),
                updatedAt = Instant.parse("2023-10-09T10:00:00Z")
            )
        )

        val savedNotes = noteDao.getAllNotes().first()

        assertEquals(expected = 9, actual = savedNotes.size)
    }

    @Test
    fun updateNote_existingEntryIsUpdated() = runTest {
        insertNotes()

        val noteToUpdate = testNoteEntity(
            id = 1L,
            title = "Updated Compose Note",
            content = "Updated content for the Compose note.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )
        noteDao.updateNote(note = noteToUpdate)

        val updatedNote = noteDao.getNoteById(1L)

        assertEquals(expected = noteToUpdate.id, actual = updatedNote?.id)
        assertEquals(expected = noteToUpdate.title, actual = updatedNote?.title)
    }

    @Test
    fun deleteNote_existingEntryIsDeleted() = runTest {
        insertNotes()

        val noteToDelete = testNoteEntity(
            id = 1L,
            title = "compose",
            content = "Compose is a modern toolkit for building native UI.",
            type = NoteType.SIMPLE,
            createdAt = Instant.parse("2023-10-01T10:00:00Z"),
            updatedAt = Instant.parse("2023-10-01T10:00:00Z")
        )
        noteDao.deleteNote(note = noteToDelete)

        val savedNotes = noteDao.getAllNotes().first()

        assertEquals(expected = 7, actual = savedNotes.size)
    }

    @Test
    fun getAllNotes_returnsAllNotes() = runTest {
        insertNotes()

        val savedNotes = noteDao.getAllNotes().first()

        assertEquals(expected = 8, actual = savedNotes.size)
        assertEquals(
            expected = LongRange(1, 8).toList().reversed(),
            actual = savedNotes.map(NoteEntity::id)
        )
    }

    @Test
    fun getNoteById_returnsCorrectNote() = runTest {
        insertNotes()

        val note = noteDao.getNoteById(1L)

        assertEquals(expected = 1L, actual = note?.id)
        assertEquals(expected = "compose", actual = note?.title)
    }

    @Test
    fun getNoteWithItems_returnsCorrectNoteWithItems() = runTest {
        insertNotes()

        val noteWithItems = noteDao.getNoteWithItems(7L)

        println(noteWithItems)

        assertNotNull(actual = noteWithItems)
        assertEquals(expected = 7L, actual = noteWithItems.note.id)
        assertEquals(expected = 4, actual = noteWithItems.items.size)
    }

    @Test
    fun getAllChecklistNotes_returnsAllChecklistNotes() = runTest {
        insertNotes()

        val checklistNotes = noteDao.getAllChecklistNotes().first()

        assertEquals(expected = 2, actual = checklistNotes.size)
        assertEquals(expected = "Meeting Notes", actual = checklistNotes[0].note.title)
        assertEquals(expected = "Grocery List", actual = checklistNotes[1].note.title)
    }

    @Test
    fun insertItem_newItemIsInserted() = runTest {
        insertNotes()

        val newItem = testItemEntity(
            id = 7L,
            content = "Eggs",
            isCompleted = false,
            noteId = 7L
        )
        noteDao.insertItem(item = newItem)

        val itemsForNote = noteDao.getItemsForNote(noteId = 7L)

        assertEquals(expected = 5, actual = itemsForNote.size)
        assertEquals(expected = "Eggs", actual = itemsForNote.last().content)
    }

    @Test
    fun insertItems_multipleItemsAreInserted() = runTest {
        insertNotes()

        val newItems = listOf(
            testItemEntity(id = 8L, content = "Bread", isCompleted = false, noteId = 7L),
            testItemEntity(id = 9L, content = "Butter", isCompleted = true, noteId = 7L)
        )
        noteDao.insertItems(items = newItems)

        val itemsForNote = noteDao.getItemsForNote(noteId = 7L)

        assertEquals(expected = 6, actual = itemsForNote.size)
        assertEquals(expected = "Bread", actual = itemsForNote[4].content)
        assertEquals(expected = "Butter", actual = itemsForNote[5].content)
    }

    @Test
    fun updateItem_existingItemIsUpdated() = runTest {
        insertNotes()

        val itemToUpdate = testItemEntity(
            id = 1L,
            content = "Updated Potato",
            isCompleted = true,
            noteId = 7L
        )
        noteDao.updateItem(item = itemToUpdate)

        val updatedItem = noteDao.getItemsForNote(noteId = 7L).firstOrNull { it.id == 1L }

        assertNotNull(actual = updatedItem)
        assertEquals(expected = "Updated Potato", actual = updatedItem.content)
        assertEquals(expected = true, actual = updatedItem.checked)
    }

    @Test
    fun deleteItem_existingItemIsDeleted() = runTest {
        insertNotes()

        val itemToDelete = testItemEntity(
            id = 1L,
            content = "Potato",
            isCompleted = false,
            noteId = 7L
        )
        noteDao.deleteItem(item = itemToDelete)

        val itemsForNote = noteDao.getItemsForNote(noteId = 7L)

        assertEquals(expected = 3, actual = itemsForNote.size)
        assertEquals(expected = "Milk", actual = itemsForNote[0].content)
    }

    @Test
    fun getItemsForNote_returnsCorrectItems() = runTest {
        insertNotes()

        val itemsForNote = noteDao.getItemsForNote(noteId = 7L)

        assertEquals(expected = 4, actual = itemsForNote.size)
        assertEquals(expected = "Potato", actual = itemsForNote[0].content)
        assertEquals(expected = "Chicken", actual = itemsForNote[3].content)
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
            noteDao.insertNote(note = it)
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

        itemEntities.forEach {
            noteDao.insertItem(item = it)
        }
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
    noteId: Long
): ItemEntity = ItemEntity(
    id = id,
    content = content,
    checked = isCompleted,
    noteId = noteId
)