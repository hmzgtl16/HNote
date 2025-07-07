package com.example.hnote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.hnote.feature.search.navigation.navigateToSearch
import com.example.hnote.feature.settings.navigation.navigateToSettings
import kotlinx.coroutines.CoroutineScope

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

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