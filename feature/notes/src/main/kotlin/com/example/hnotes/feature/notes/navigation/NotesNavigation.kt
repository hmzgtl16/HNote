package com.example.hnotes.feature.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hnotes.core.navigation.Route
import com.example.hnotes.feature.notes.NotesRoute

fun NavGraphBuilder.notesScreen() {
    composable<Route.Notes> {
        NotesRoute()
    }
}