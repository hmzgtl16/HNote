package com.example.notes.feature.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.notes.feature.notes.NotesRoute
import kotlinx.serialization.Serializable

@Serializable
data object NotesRoute

fun NavController.navigateToNotes(navOptions: NavOptions? = null) =
    navigate(route = NotesRoute, navOptions = navOptions)

fun NavGraphBuilder.notesScreen(
    onNoteClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<NotesRoute> {
        NotesRoute(onNoteClick = onNoteClick)
    }
}