package com.example.hnote.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.hnote.core.data.repository.NoteRepository
import com.example.hnote.core.model.Item
import com.example.hnote.core.model.Note
import com.example.hnote.core.navigation.Navigator
import com.example.hnote.core.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val navigator: Navigator,
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    private val undoStack = ArrayDeque<EditableNoteState>()
    private val redoStack = ArrayDeque<EditableNoteState>()

    private val maxHistorySize = 30

    init {
        savedStateHandle.toRoute<Route.Note>().noteId?.let { id ->
            viewModelScope.launch {
                noteRepository.getNoteById(id = id)
                    .filterNotNull()
                    .collect { note ->
                        val initialEditableState = EditableNoteState(
                            title = note.title,
                            content = note.content,
                            backgroundColor = note.backgroundColor,
                            reminder = note.reminder,
                            items = note.items
                        )

                        clearUndoRedoStacks()

                        _uiState.update {
                            it.copy(
                                note = note,
                                title = initialEditableState.title,
                                content = initialEditableState.content,
                                backgroundColor = initialEditableState.backgroundColor,
                                reminder = initialEditableState.reminder,
                                items = initialEditableState.items,
                                isEdited = false
                            )
                        }
                    }
            }
        } ?: run {
            clearUndoRedoStacks()

            _uiState.update {
                it.copy(
                    canUndo = undoStack.isNotEmpty(),
                    canRedo = redoStack.isNotEmpty()
                )
            }
        }
    }

    fun onEvent(event: NoteScreenEvent) {
        val currentStateSnapshot = uiState.value.getEditableSnapshot()

        when (event) {
            is NoteScreenEvent.TitleChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(title = event.title))
            }

            is NoteScreenEvent.ContentChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(content = event.content))
            }

            is NoteScreenEvent.BackgroundColorChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(backgroundColor = event.color))
            }

            is NoteScreenEvent.ReminderChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(reminder = event.reminder))
            }

            is NoteScreenEvent.AddItem -> handleEditableStateChange {
                val items = it.items.toMutableList()
                items.add(element = Item())
                it.copy(items = it.items + Item())
            }

            is NoteScreenEvent.RemoveItem -> handleEditableStateChange {
                val items = it.items.toMutableList()
                items.removeAt(index = event.index)
                it.copy(items = items.toList())
            }

            is NoteScreenEvent.UpdateItem -> handleEditableStateChange {
                val items = it.items.toMutableList()
                items.set(index = event.index, element = event.item)
                it.copy(items = items.toList())
            }

            is NoteScreenEvent.ReminderPickerVisibilityChanged -> _uiState.update {
                it.copy(isReminderPickerVisible = event.isVisible)
            }

            is NoteScreenEvent.PaletteVisibilityChanged -> _uiState.update {
                it.copy(isPaletteVisible = event.isVisible)
            }

            is NoteScreenEvent.DeleteDialogVisibilityChanged -> _uiState.update {
                it.copy(isDeleteDialogVisible = event.isVisible)
            }

            is NoteScreenEvent.Undo -> undo()
            is NoteScreenEvent.Redo -> redo()
            is NoteScreenEvent.SaveNote -> saveNote()
            is NoteScreenEvent.CopyNote -> copyNote()
            is NoteScreenEvent.DeleteNote -> deleteNote()
        }
    }

    private fun updateEditableState(
        newEditableState: EditableNoteState,
        markAsEdited: Boolean = true
    ) {
        _uiState.update {
            it.copy(
                title = newEditableState.title,
                content = newEditableState.content,
                backgroundColor = newEditableState.backgroundColor,
                reminder = newEditableState.reminder,
                isEdited = if (markAsEdited) true else it.isEdited,
                canUndo = undoStack.isNotEmpty(),
                canRedo = redoStack.isNotEmpty()
            )
        }
    }

    private fun addChangeToHistory(previousState: EditableNoteState) {
        if (undoStack.size >= MAX_HISTORY_SIZE) {
            undoStack.removeFirst()
        }
        undoStack.addLast(previousState)
        redoStack.clear()
        if (!uiState.value.isEdited) {
            _uiState.update { it.copy(isEdited = true) }
        }
    }

    private fun handleEditableStateChange(updateAction: (EditableNoteState) -> EditableNoteState) {
        val currentStateSnapshot = uiState.value.getEditableSnapshot()
        addChangeToHistory(previousState = currentStateSnapshot)
        updateEditableState(newEditableState = updateAction(currentStateSnapshot))
    }

    private fun clearUndoRedoStacks() {
        undoStack.clear()
        redoStack.clear()
        _uiState.update {
            it.copy(
                canUndo = undoStack.isNotEmpty(),
                canRedo = redoStack.isNotEmpty()
            )
        }
    }

    private fun undo() {
        if (undoStack.isNotEmpty()) {
            val stateToRestore = undoStack.removeLast()
            val currentStateForRedo = uiState.value.getEditableSnapshot()
            redoStack.addLast(currentStateForRedo)
            updateEditableState(stateToRestore)
        }
    }

    private fun redo() {
        if (redoStack.isNotEmpty()) {
            val stateToRestore = redoStack.removeLast()
            val currentStateForUndo = uiState.value.getEditableSnapshot()
            undoStack.addLast(currentStateForUndo)
            updateEditableState(stateToRestore)
        }
    }

    private fun saveNote() = viewModelScope.launch {
        val currentState = uiState.value
        if (!currentState.isEdited) return@launch

        val noteToSave = currentState.note?.copy(
            title = currentState.title,
            content = currentState.content,
            reminder = currentState.reminder,
            backgroundColor = currentState.backgroundColor,
            updated = Clock.System.now()
        ) ?: Note()
        noteRepository.updateNote(note = noteToSave)
    }

    private fun copyNote() = viewModelScope.launch {
        val currentState = uiState.value

        val noteToCopy = currentState.note?.copy(
            id = 0,
            created = Clock.System.now(),
            updated = Clock.System.now()
        ) ?: return@launch
        noteRepository.updateNote(note = noteToCopy)
    }

    private fun deleteNote() = viewModelScope.launch {
        val currentState = uiState.value
        val noteToDelete = currentState.note ?: return@launch
        noteRepository.deleteNote(note = noteToDelete)
        navigateBack()
    }

    fun navigateBack() = viewModelScope.launch {
        navigator.navigateBack()
    }

    companion object {
        private const val MAX_HISTORY_SIZE = 30
    }
}

