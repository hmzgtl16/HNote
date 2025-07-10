package com.example.hnote.feature.notes

import com.example.hnote.core.model.Note

sealed interface NotesState {
    data object Loading : NotesState
    data class Success(val notes: Map<Boolean, List<Note>>) : NotesState
}

data class NotesUiState(
    val notesState: NotesState = NotesState.Loading,
    val deletedNotes: List<Note> = emptyList(),
    val selectedNotes: List<Note> = emptyList(),
    val isMultiSelectionEnabled: Boolean = false
)