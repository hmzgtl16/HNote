package com.example.hnotes.navigation

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.hnotes.core.navigation.Route
import com.example.hnotes.feature.note.navigation.noteScreen
import com.example.hnotes.feature.notes.navigation.notesScreen
import com.example.hnotes.feature.search.navigation.searchScreen
import com.example.hnotes.feature.settings.navigation.settingsDialog
import com.example.hnotes.ui.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = Route.Notes,
        modifier = modifier,
        builder = {
            notesScreen()
            noteScreen()
            searchScreen()
            settingsDialog()
        }
    )
}

