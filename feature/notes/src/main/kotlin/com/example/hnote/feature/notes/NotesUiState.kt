package com.example.hnote.feature.notes

import androidx.compose.ui.state.ToggleableState
import com.example.hnote.core.model.Note

sealed interface NotesState {
    data object Loading : NotesState
    data class Success(val notes: Map<Boolean, List<Note>>) : NotesState
}

data class NotesUiState(
    val notesState: NotesState = NotesState.Loading,
    val selectedNotes: List<Note> = emptyList(),
    val isMultiSelectionEnabled: Boolean = false,
    val recentlyDeletedNotes: List<Note> = emptyList(),
    val showUndoDeleteSnackbar: Boolean = false
)

val NotesUiState.allNotesSelectedState: ToggleableState
    get() {
        return when (val notesState = this.notesState) {
            is NotesState.Success -> {
                val allNotes = notesState.notes.values.flatten()
                when {
                    this.selectedNotes.isEmpty() -> ToggleableState.Off
                    allNotes.isNotEmpty() && this.selectedNotes.containsAll(allNotes) -> ToggleableState.On
                    this.selectedNotes.isNotEmpty() -> ToggleableState.Indeterminate
                    else -> ToggleableState.Off
                }
            }

            else -> ToggleableState.Off
        }
    }