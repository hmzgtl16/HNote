package com.example.hnote.core.data.repository

import com.example.hnote.core.model.SearchQuery
import com.example.hnote.core.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult>
    fun getAllSearchQueries(limit: Int): Flow<List<SearchQuery>>
    suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery)
    suspend fun delete(searchQuery: SearchQuery)
    suspend fun deleteAll()
}