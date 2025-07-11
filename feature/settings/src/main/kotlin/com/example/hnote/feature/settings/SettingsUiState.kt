package com.example.hnote.feature.settings

import com.example.hnote.core.model.Theme

sealed interface SettingsUiState {

    data object Loading : SettingsUiState
    data class Success(val theme: Theme, val useDynamicColor: Boolean) : SettingsUiState
}