package com.example.hnote.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnote.core.data.repository.NoteRepository
import com.example.hnote.core.model.Note
import com.example.hnote.core.navigation.Navigator
import com.example.hnote.core.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val navigator: Navigator,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = NotesUiState())
    val uiState: StateFlow<NotesUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.notes
                .map<List<Note>, NotesState> {
                    val groupedNotes = it
                        .groupBy(Note::pinned)
                        .toSortedMap(compareByDescending(Boolean::not))
                    NotesState.Success(notes = groupedNotes)
                }
                .onStart { emit(NotesState.Loading) }
                .collect { state ->
                    _uiState.update { it.copy(notesState = state) }
                }
        }
    }

    fun onEvent(event: NotesScreenEvent) {
        when (event) {
            is NotesScreenEvent.MultiSelectionChanged -> _uiState.update {
                it.copy(isMultiSelectionEnabled = event.enabled)
            }

            is NotesScreenEvent.NoteSelectedChanged -> _uiState.update {
                val updatedSelectedNotes = it.selectedNotes
                    .toMutableList()
                    .apply {
                        if (contains(event.note))
                            remove(event.note)
                        else
                            add(event.note)
                    }
                it.copy(selectedNotes = updatedSelectedNotes)

            }

            is NotesScreenEvent.SelectAllNotesChecked -> _uiState.update {
                val allNotes = (it.notesState as? NotesState.Success)
                    ?.notes?.values?.flatten() ?: emptyList()
                it.copy(selectedNotes = if (event.checked) allNotes else emptyList())
            }

            is NotesScreenEvent.DeleteNotes -> deleteNotes()
            is NotesScreenEvent.RestoreNotes -> restoreNotes()
            is NotesScreenEvent.PinNote -> pinNote(note = event.note)
            is NotesScreenEvent.PinNotes -> pinNotes(isPinned = event.isPinned)
            is NotesScreenEvent.NavigateToNote -> navigateToNote(noteId = event.noteId)
        }
    }

    private fun deleteNotes() = viewModelScope.launch {
        val notesToDelete = _uiState.value.selectedNotes
        if (notesToDelete.isEmpty()) return@launch

        noteRepository.deleteNotes(notes = notesToDelete)

        _uiState.update {
            it.copy(
                deletedNotes = notesToDelete,
                selectedNotes = emptyList(),
                isMultiSelectionEnabled = false
            )
        }
    }

    private fun restoreNotes() = viewModelScope.launch {
        val notesToRestore = _uiState.value.deletedNotes
        if (notesToRestore.isEmpty()) return@launch

        noteRepository.updateNotes(notes = notesToRestore)
        _uiState.update {
            it.copy(deletedNotes = emptyList())
        }
    }

    private fun pinNote(note: Note) = viewModelScope.launch {
        val noteToPin = note.copy(pinned = !note.pinned)
        noteRepository.updateNote(note = noteToPin)
    }

    private fun pinNotes(isPinned: Boolean) = viewModelScope.launch {
        val notesToPin = _uiState.value.selectedNotes.map { it.copy(pinned = isPinned) }
        if (notesToPin.isEmpty()) return@launch

        noteRepository.updateNotes(notes = notesToPin)
        _uiState.update {
            it.copy(
                selectedNotes = emptyList(),
                isMultiSelectionEnabled = false
            )
        }
    }

    private fun navigateToNote(noteId: Long) = viewModelScope.launch {
        navigator.navigateTo(route = Route.Note(noteId = noteId))
    }
}
