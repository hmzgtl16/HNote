package com.example.hnote.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemFtsDao {

    @Query("SELECT item_fts_id FROM items_fts WHERE items_fts MATCH :text")
    fun searchAllItems(text: String): Flow<List<String>>
}