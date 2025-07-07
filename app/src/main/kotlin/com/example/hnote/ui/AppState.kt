package com.example.hnote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hnote.feature.search.navigation.navigateToSearch
import com.example.hnote.feature.settings.navigation.navigateToSettings
import com.example.notes.feature.notes.navigation.NotesRoute
import kotlinx.coroutines.CoroutineScope

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val isMainDestination: Boolean
        @Composable get() = currentDestination?.hasRoute(route = NotesRoute::class) ?: false

    fun navigateToSearch() = navController.navigateToSearch()
    fun navigateToSettings() = navController.navigateToSettings()
}

@Composable
fun rememberAppState(
    navController: NavHostController,
    coroutineScope: CoroutineScope
): AppState = remember(
    key1 = navController,
    key2 = coroutineScope,
    calculation = { AppState(navController = navController, coroutineScope = coroutineScope) }
)