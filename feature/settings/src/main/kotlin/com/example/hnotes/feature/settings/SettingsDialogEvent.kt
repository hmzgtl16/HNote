package com.example.hnotes.feature.settings

import com.example.hnotes.core.model.Theme

sealed interface SettingsDialogEvent {
    data object Dismiss : SettingsDialogEvent
    data class ThemeChanged(val theme: Theme) : SettingsDialogEvent
    data class DynamicColorEnabled(val enabled: Boolean) : SettingsDialogEvent

}