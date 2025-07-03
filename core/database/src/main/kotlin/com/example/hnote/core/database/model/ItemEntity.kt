package com.example.hnote.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = arrayOf("note_id"),
            childColumns = arrayOf("item_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("noteId")]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String,
    val isCompleted: Boolean,
    val noteId: Long
)
