package com.example.hnote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

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