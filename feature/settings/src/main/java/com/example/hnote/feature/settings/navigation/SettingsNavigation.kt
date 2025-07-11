package com.example.hnote.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.settings.SettingsRoute

fun NavGraphBuilder.settingsDialog() {

    dialog<Route.Settings> {
        SettingsRoute()
    }
}
