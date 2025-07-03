package com.example.hnote.core.data.repository

import com.example.hnote.core.datastore.PreferencesDataSource
import com.example.hnote.core.model.Theme
import com.example.hnote.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPreferenceSource: PreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData>
        get() = TODO("Not yet implemented")

    override suspend fun setTheme(theme: Theme) {
        TODO("Not yet implemented")
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        TODO("Not yet implemented")
    }


}