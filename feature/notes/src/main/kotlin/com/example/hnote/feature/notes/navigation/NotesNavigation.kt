package com.example.hnote.feature.notes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.notes.NotesRoute

fun NavGraphBuilder.notesScreen() {
    composable<Route.Notes> {
        NotesRoute()
    }
}