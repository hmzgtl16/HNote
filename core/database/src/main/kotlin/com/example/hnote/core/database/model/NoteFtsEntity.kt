package com.example.hnote.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "notes_fts")
@Fts4
data class NoteFtsEntity(
    @ColumnInfo(name = "note_fts_id")
    val id: Long,
    @ColumnInfo(name = "note_fts_title")
    val title: String,
    @ColumnInfo(name = "note_fts_content")
    val content: String
)
