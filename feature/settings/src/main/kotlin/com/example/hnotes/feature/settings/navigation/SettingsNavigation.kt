package com.example.hnotes.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.example.hnotes.core.navigation.Route
import com.example.hnotes.feature.settings.SettingsRoute

fun NavGraphBuilder.settingsDialog() {

    dialog<Route.Settings> {
        SettingsRoute()
    }
}
