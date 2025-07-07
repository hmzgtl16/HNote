package com.example.hnote.feature.search

import com.example.hnote.core.model.SearchResult

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState
    data object EmptyQuery : SearchResultUiState
    data object LoadFailed : SearchResultUiState
    data class Success(val searchResult: SearchResult = SearchResult()) : SearchResultUiState {
        fun isEmpty(): Boolean = searchResult.isEmpty()
    }
}
