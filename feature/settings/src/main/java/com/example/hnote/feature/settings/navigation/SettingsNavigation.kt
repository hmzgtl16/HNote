package com.example.hnote.feature.settings.navigation

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import com.example.hnote.feature.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    navigate(route = SettingsRoute, navOptions = navOptions)

fun NavGraphBuilder.settingsDialog(onDismiss: () -> Unit) {

    dialog<SettingsRoute>(
        dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        content = {

            SettingsRoute(onDismiss = onDismiss)
        }
    )
}