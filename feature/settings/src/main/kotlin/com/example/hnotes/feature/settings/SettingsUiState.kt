package com.example.hnotes.feature.settings

import com.example.hnotes.core.model.Theme

sealed interface SettingsUiState {

    data object Loading : SettingsUiState
    data class Success(val theme: Theme, val useDynamicColor: Boolean) : SettingsUiState
}