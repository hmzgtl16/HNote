package com.example.notes.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnote.core.data.repository.NoteRepository
import com.example.hnote.core.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    noteRepository: NoteRepository
) : ViewModel() {

    val uiState: StateFlow<NotesUiState> = noteRepository.notes
        .map<List<Note>, NotesUiState>(NotesUiState::Success)
        .onStart { emit(NotesUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = NotesUiState.Loading
        )

    private val _selectedNotes = MutableStateFlow(value = emptyList<Note>())
    val selectedNotes: StateFlow<List<Note>> get() = _selectedNotes

    private val _multiSelectionEnabled = MutableStateFlow(value = false)
    val multiSelectionEnabled: StateFlow<Boolean> get() = _multiSelectionEnabled

    fun onMultiSelectionChanged(isMultiSelectionEnabled: Boolean) =
        _multiSelectionEnabled.update { isMultiSelectionEnabled }
}
