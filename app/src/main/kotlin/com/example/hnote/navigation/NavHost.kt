package com.example.hnote.navigation

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.note.navigation.noteScreen
import com.example.hnote.feature.notes.navigation.notesScreen
import com.example.hnote.feature.search.navigation.searchScreen
import com.example.hnote.feature.settings.navigation.settingsDialog
import com.example.hnote.ui.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = Route.Notes,
        modifier = modifier,
        builder = {
            notesScreen(onShowSnackbar = onShowSnackbar)
            noteScreen(onShowSnackbar = onShowSnackbar)
            searchScreen()
            settingsDialog()
        }
    )
}

