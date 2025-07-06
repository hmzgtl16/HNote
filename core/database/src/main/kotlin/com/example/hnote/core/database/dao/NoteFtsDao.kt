package com.example.hnote.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteFtsDao {
    @Query("SELECT note_fts_id FROM notes_fts WHERE notes_fts MATCH :text")
    fun searchAllNotes(text: String): Flow<List<Long>>
}