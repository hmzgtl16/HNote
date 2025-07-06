package com.example.hnote.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.hnote.core.design.component.AppBackground
import com.example.hnote.core.design.component.AppGradientBackground

@Composable
fun App(
    appState: AppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {

    AppBackground(
        modifier = modifier,
        content = {
            AppGradientBackground {
                val snackbarHostState = remember { SnackbarHostState() }

                App(
                    appState = appState,
                    snackbarHostState = snackbarHostState,
                    windowAdaptiveInfo = windowAdaptiveInfo
                )
            }
        })
}

@Composable
fun App(
    appState: AppState,
    snackbarHostState: SnackbarHostState,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    modifier: Modifier = Modifier,
) {

}
