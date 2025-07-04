package com.example.hnote.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.model.ItemEntity
import com.example.hnote.core.database.model.NoteEntity
import com.example.hnote.core.database.util.NoteType
import com.example.hnote.core.database.util.ReminderRepeatMode
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Before

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
    noteId: Long
): ItemEntity = ItemEntity(
    id = id,
    content = content,
    checked = isCompleted,
    noteId = noteId
)