package com.example.hnote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.hnote.ui.AppState
import com.example.notes.feature.notes.navigation.NotesRoute
import com.example.notes.feature.notes.navigation.notesScreen

@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = NotesRoute,
        modifier = modifier,
        builder = {
            notesScreen(
                onNoteClick = {},
                onShowSnackbar = onShowSnackbar,
            )
        }
    )
}

