package com.example.hnote.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Fts4

@Entity(
    tableName = "items_fts",
    foreignKeys = [
        ForeignKey(
            entity = NoteFtsEntity::class,
            parentColumns = ["note_fts_id"],
            childColumns = ["item_fts_noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Fts4
data class ItemFtsEntity(
    @ColumnInfo(name = "item_fts_id")
    val id: Long,
    @ColumnInfo(name = "item_fts_content")
    val content: String,
    @ColumnInfo(name = "item_fts_noteId")
    val noteId: Long
)
