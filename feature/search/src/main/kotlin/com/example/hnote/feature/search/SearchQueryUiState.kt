package com.example.hnote.feature.search

import com.example.hnote.core.model.SearchQuery

sealed interface SearchQueryUiState {
    data object Loading : SearchQueryUiState
    data class Success(val queries: List<SearchQuery> = emptyList()) : SearchQueryUiState
}
