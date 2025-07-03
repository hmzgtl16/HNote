package com.example.hnote.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnote.core.database.util.NoteType
import com.example.hnote.core.database.util.ReminderRepeatMode
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val pinned: Boolean = false,
    val backgroundColor: Int? = null,
    val type: NoteType,
    val reminder: Instant? = null,
    val reminderMode: ReminderRepeatMode? = null,
    val isCompleted: Boolean? = null,
)
