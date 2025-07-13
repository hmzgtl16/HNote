package com.example.hnote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hnote.core.navigation.Navigator
import com.example.hnote.core.navigation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class AppState(
    val navigator: Navigator,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val isMainDestination: Boolean
        @Composable get() = currentDestination?.hasRoute(route = Route.Notes::class) ?: false

    fun navigateToSearch() = coroutineScope.launch {
        navigator.navigateTo(Route.Search)
    }

    fun navigateToSettings() = coroutineScope.launch {
        navigator.navigateTo(Route.Settings)
    }
}

@Composable
fun rememberAppState(
    navigator: Navigator,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(
    navigator,
    navController,
    coroutineScope
) {
    AppState(
        navigator = navigator,
        navController = navController,
        coroutineScope = coroutineScope
    )
}