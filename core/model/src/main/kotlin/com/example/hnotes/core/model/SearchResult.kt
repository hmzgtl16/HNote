package com.example.hnotes.core.model

data class SearchResult(
    val notes: List<Note> = emptyList()
) {
    fun isEmpty(): Boolean = notes.isEmpty()
}
