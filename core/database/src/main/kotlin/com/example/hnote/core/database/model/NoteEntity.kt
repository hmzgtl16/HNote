package com.example.hnote.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnote.core.database.util.NoteType
import kotlinx.datetime.Instant

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val pinned: Boolean = false,
    val backgroundColor: Int? = null,
    val type: NoteType,
    val createdAt: Instant,
    val updatedAt: Instant
)
