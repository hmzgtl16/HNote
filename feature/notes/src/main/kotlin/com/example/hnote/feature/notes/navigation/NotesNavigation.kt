package com.example.hnote.feature.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.notes.NotesRoute

fun NavGraphBuilder.notesScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<Route.Notes> {
        NotesRoute()
    }
}