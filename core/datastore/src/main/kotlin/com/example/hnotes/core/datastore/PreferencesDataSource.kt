package com.example.hnotes.core.datastore

import androidx.datastore.core.DataStore
import com.example.hnotes.core.datastore.UserPreferences
import com.example.hnotes.core.datastore.copy
import com.example.hnotes.core.model.Theme
import com.example.hnotes.core.model.UserData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData(
                theme = it.uiThemeConfig.asTheme(),
                useDynamicColor = it.useDynamicUiTheme
            )
        }

    suspend fun setTheme(theme: Theme) {
        userPreferences.updateData {
            it.copy {
                uiThemeConfig = theme.asProto()
            }
        }
    }

    suspend fun setDynamicColor(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy { useDynamicUiTheme = useDynamicColor }
        }
    }
}