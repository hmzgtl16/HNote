package com.example.notes.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnote.core.data.repository.NoteRepository
import com.example.hnote.core.domain.usecase.GetNotesUseCase
import com.example.hnote.core.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    val uiState: StateFlow<NotesUiState> = getNotesUseCase.invoke()
        .map<Map<Boolean, List<Note>>, NotesUiState>(NotesUiState::Success)
        .onStart { emit(NotesUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = NotesUiState.Loading
        )

    private val _isDeleteSuccess = MutableStateFlow(value = false)
    val isDeleteSuccess: StateFlow<Boolean> get() = _isDeleteSuccess

    private val _deletedNotes = MutableStateFlow(value = emptyList<Note>())
    val deletedNotes: StateFlow<List<Note>> get() = _deletedNotes

    private val _selectedNotes = MutableStateFlow(value = emptyList<Note>())
    val selectedNotes: StateFlow<List<Note>> get() = _selectedNotes

    private val _multiSelectionEnabled = MutableStateFlow(value = false)
    val multiSelectionEnabled: StateFlow<Boolean> get() = _multiSelectionEnabled

    fun onMultiSelectionChanged(isMultiSelectionEnabled: Boolean) =
        _multiSelectionEnabled.update { isMultiSelectionEnabled }

    fun onNoteSelectedChanged(note: Note) = _selectedNotes.update {
        val modified = it.toMutableList()

        if (modified.contains(element = note)) modified.remove(element = note)
        else modified.add(element = note)

        modified
    }

    fun onSelectAllNotesChecked(value: Boolean) {
        if (value) _selectedNotes.value =
            (uiState.value as NotesUiState.Success).notes.values.flatten()
        else _selectedNotes.value = emptyList()
    }

    fun deleteNotes() = viewModelScope.launch {
        noteRepository.deleteNotes(notes = selectedNotes.value)
        _deletedNotes.value = selectedNotes.value
        _selectedNotes.value = emptyList()
        _multiSelectionEnabled.value = false
    }

    fun restoreNotes() = viewModelScope.launch {
        noteRepository.updateNotes(notes = deletedNotes.value)
        _deletedNotes.value = emptyList()
        _isDeleteSuccess.value = false
    }

    fun pinNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note = note.copy(pinned = !note.pinned))
    }

    fun pinNotes(isPinned: Boolean) = viewModelScope.launch {
        noteRepository.updateNotes(notes = selectedNotes.value.map { it.copy(pinned = isPinned) })
        _selectedNotes.value = emptyList()
        _multiSelectionEnabled.value = false
    }
}
