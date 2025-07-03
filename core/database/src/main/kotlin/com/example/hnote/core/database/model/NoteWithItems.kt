package com.example.hnote.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class NoteWithItems(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val items: List<ItemEntity>
)

