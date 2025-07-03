package com.example.hnote.core.datastore

import androidx.datastore.core.DataStore
import com.example.hnote.core.model.UserData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData(
                theme = it.uiThemeConfig.asTheme(),
                it.useDynamicUiTheme
            )
        }
}