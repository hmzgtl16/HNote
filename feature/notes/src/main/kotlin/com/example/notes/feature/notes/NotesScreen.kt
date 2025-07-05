package com.example.notes.feature.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun NotesRoute(
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
) {

}