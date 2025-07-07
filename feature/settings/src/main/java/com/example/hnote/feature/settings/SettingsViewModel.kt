package com.example.hnote.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnote.core.data.repository.UserDataRepository
import com.example.hnote.core.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {


    val uiState: StateFlow<SettingsUiState> = userDataRepository.userData
        .map {
            SettingsUiState.Success(
                theme = it.theme,
                useDynamicColor = it.useDynamicColor
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = SettingsUiState.Loading,
        )

    fun updateTheme(theme: Theme) = viewModelScope.launch {
        userDataRepository.setTheme(theme = theme)
    }

    fun updateDynamicColor(useDynamicColor: Boolean) = viewModelScope.launch {
        userDataRepository.setDynamicColorPreference(useDynamicColor)
    }
}

