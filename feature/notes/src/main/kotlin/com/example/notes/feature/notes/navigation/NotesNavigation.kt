package com.example.notes.feature.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object NotesRoute

fun NavController.navigateToNotes(navOptions: NavOptions) =
    navigate(route = NotesRoute, navOptions = navOptions)

fun NavGraphBuilder.notesScreen(
    onNoteClick: (Long) -> Unit
) {
    composable<NotesRoute> {
        NotesRoute(onNoteClick = onNoteClick)
    }
}