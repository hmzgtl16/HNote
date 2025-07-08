package com.example.hnote.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.hnote.core.database.util.ReminderRepeatMode
import kotlinx.datetime.Instant

@Entity(
    tableName = "reminders",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val time: Instant,
    val repeatMode: ReminderRepeatMode,
    val completed: Boolean,
    @ColumnInfo(index = true) val noteId: Long
)
