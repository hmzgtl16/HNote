package com.example.hnote.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val content: String,
    val checked: Boolean = false,
    @ColumnInfo(index = true) val noteId: Long
)
