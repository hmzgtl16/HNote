package com.example.hnote.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.hnote.core.database.model.SearchQueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchQueryDao {

    @Query(value = "SELECT * FROM search_queries ORDER BY queried DESC LIMIT :limit")
    fun getSearchQueries(limit: Int): Flow<List<SearchQueryEntity>>

    @Upsert
    suspend fun upsertSearchQuery(searchQuery: SearchQueryEntity)

    @Query(value = "DELETE FROM search_queries")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(searchQuery: SearchQueryEntity)
}