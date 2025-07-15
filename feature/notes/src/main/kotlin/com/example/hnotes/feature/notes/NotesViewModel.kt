package com.example.hnotes.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.core.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private var undoDeleteJob: Job? = null

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
                val updatedSelectedNotes =
                    if (it.selectedNotes.contains(event.note)) it.selectedNotes - event.note
                    else it.selectedNotes + event.note

                it.copy(selectedNotes = updatedSelectedNotes)
            }

            is NotesScreenEvent.SelectAllNotesChecked -> _uiState.update {
                val allNotes = (it.notesState as? NotesState.Success)
                    ?.notes?.values?.flatten() ?: emptyList()
                it.copy(selectedNotes = if (event.checked) allNotes else emptyList())
            }

            is NotesScreenEvent.DeleteNotes -> deleteNotes()
            is NotesScreenEvent.RestoreNotes -> restoreNotes()

            is NotesScreenEvent.UndoSnackbarDismissed -> {
                _uiState.update { it.copy(showUndoDeleteSnackbar = false) }
            }

            is NotesScreenEvent.PinNote -> pinNote(note = event.note)
            is NotesScreenEvent.PinNotes -> pinNotes()
            is NotesScreenEvent.NavigateToNote -> navigateToNote(noteId = event.noteId)
        }
    }

    private fun deleteNotes() = viewModelScope.launch {
        val notesToDelete = _uiState.value.selectedNotes
        if (notesToDelete.isEmpty()) return@launch

        noteRepository.deleteNotes(notes = notesToDelete)

        _uiState.update {
            it.copy(
                selectedNotes = emptyList(),
                isMultiSelectionEnabled = false,
                recentlyDeletedNotes = notesToDelete,
                showUndoDeleteSnackbar = true
            )
        }

        undoDeleteJob?.cancel()
        undoDeleteJob = viewModelScope.launch {
            delay(5000L)
            _uiState.update {
                if (it.recentlyDeletedNotes == notesToDelete) {
                    it.copy(recentlyDeletedNotes = emptyList(), showUndoDeleteSnackbar = false)
                } else {
                    it
                }
            }
        }
    }

    private fun restoreNotes() = viewModelScope.launch {
        undoDeleteJob?.cancel()
        val notesToRestore = _uiState.value.recentlyDeletedNotes
        if (notesToRestore.isEmpty()) return@launch

        noteRepository.updateNotes(notes = notesToRestore)
        _uiState.update {
            it.copy(recentlyDeletedNotes = emptyList(), showUndoDeleteSnackbar = false)
        }
    }

    private fun pinNote(note: Note) = viewModelScope.launch {
        val noteToPin = note.copy(pinned = !note.pinned)
        noteRepository.upsertNote(note = noteToPin)
    }

    private fun pinNotes() = viewModelScope.launch {
        val selectedNotes = _uiState.value.selectedNotes
        if (selectedNotes.isEmpty()) return@launch

        val shouldPin = selectedNotes.any { !it.pinned }
        val notesToPin = selectedNotes.map { it.copy(pinned = shouldPin) }
        noteRepository.updateNotes(notes = notesToPin)
        _uiState.update {
            it.copy(
                selectedNotes = emptyList(),
                isMultiSelectionEnabled = false
            )
        }
    }

    private fun navigateToNote(noteId: Long?) = viewModelScope.launch {
        navigator.navigateTo(route = Route.Note(noteId = noteId))
    }
}
