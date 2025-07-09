package com.example.hnote.feature.note.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.note.NoteRoute

fun NavGraphBuilder.noteScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<Route.Note> {
        NoteRoute()
    }
}