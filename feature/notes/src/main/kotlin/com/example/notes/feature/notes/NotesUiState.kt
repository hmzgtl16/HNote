package com.example.notes.feature.notes

import com.example.hnote.core.model.Note

sealed interface NotesUiState {

    data object Loading : NotesUiState
    data class Success(val notes: List<Note>) : NotesUiState
}
