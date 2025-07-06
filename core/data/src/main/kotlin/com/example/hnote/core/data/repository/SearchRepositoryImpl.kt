package com.example.hnote.core.data.repository

import com.example.hnote.core.model.SearchQuery
import com.example.hnote.core.model.SearchResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {
    override suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult> {
        TODO("Not yet implemented")
    }

    override fun getAllSearchQueries(limit: Int): Flow<List<SearchQuery>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(searchQuery: SearchQuery) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}