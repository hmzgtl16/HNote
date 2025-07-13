package com.example.hnote.feature.note.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.hnote.core.navigation.DEEP_LINK_SCHEME_AND_HOST
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.note.NoteRoute

fun NavGraphBuilder.noteScreen() {
    composable<Route.Note>(
        deepLinks = listOf(navDeepLink<Route.Note>(basePath = DEEP_LINK_SCHEME_AND_HOST))
    ) {
        NoteRoute()
    }
}