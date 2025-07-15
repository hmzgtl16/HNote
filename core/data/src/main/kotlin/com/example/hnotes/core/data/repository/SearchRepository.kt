package com.example.hnotes.core.data.repository

import com.example.hnotes.core.model.SearchQuery
import com.example.hnotes.core.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult>
    fun getAllSearchQueries(limit: Int): Flow<List<SearchQuery>>
    suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery)
    suspend fun delete(searchQuery: SearchQuery)
    suspend fun deleteAll()
}