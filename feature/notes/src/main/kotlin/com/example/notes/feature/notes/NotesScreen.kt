package com.example.notes.feature.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnote.core.model.Note

@Composable
internal fun NotesRoute(
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedNotes by viewModel.selectedNotes.collectAsStateWithLifecycle()
    val multiSelectionEnabled by viewModel.multiSelectionEnabled.collectAsStateWithLifecycle()

    NotesScreen(
        uiState = uiState,
        selectedNotes = selectedNotes,
        multiSelectionEnabled = multiSelectionEnabled,
        onMultiSelectionChanged = {},
        onSelectAllNotesChecked = {},
        noteSelected = { false },
        onNoteSelectedChanged = {},
        deletedNotes = emptyList(),
        pinNotes = {},
        onNoteClick = onNoteClick,
        onPinClick = {},
        deleteNotes = {},
        isDeleteSuccess = false,
        restoreNotes = {},
        modifier = modifier
    )
}

@Composable
internal fun NotesScreen(
    uiState: NotesUiState,
    selectedNotes: List<Note>,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    onSelectAllNotesChecked: (Boolean) -> Unit,
    noteSelected: (Note) -> Boolean,
    onNoteSelectedChanged: (Note) -> Unit,
    deleteNotes: () -> Unit,
    pinNotes: (Boolean) -> Unit,
    onNoteClick: (Long) -> Unit,
    onPinClick: (Note) -> Unit,
    deletedNotes: List<Note>,
    isDeleteSuccess: Boolean,
    restoreNotes: () -> Unit,
    modifier: Modifier = Modifier,
) {

}