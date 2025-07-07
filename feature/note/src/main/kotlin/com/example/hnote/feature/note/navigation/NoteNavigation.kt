package com.example.hnote.feature.note.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnote.feature.note.NoteRoute
import kotlinx.serialization.Serializable

@Serializable
data class NoteRoute(val noteId: Long)

fun NavController.navigateToNote(noteId: Long, navOptions: NavOptions? = null) =
    navigate(route = NoteRoute(noteId = noteId), navOptions = navOptions)

fun NavGraphBuilder.noteScreen(
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<NoteRoute> {
        NoteRoute(onBackClick = onBackClick)
    }
}