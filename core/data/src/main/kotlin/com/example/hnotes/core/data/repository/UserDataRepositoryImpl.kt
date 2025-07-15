package com.example.hnotes.core.data.repository

import com.example.hnotes.core.datastore.PreferencesDataSource
import com.example.hnotes.core.model.Theme
import com.example.hnotes.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPreferenceSource: PreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData> = userPreferenceSource.userData

    override suspend fun setTheme(theme: Theme) {
        userPreferenceSource.setTheme(theme = theme)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferenceSource.setDynamicColor(useDynamicColor = useDynamicColor)
    }
}