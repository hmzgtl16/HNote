package com.example.hnote.core.data.repository

import com.example.hnote.core.model.Theme
import com.example.hnote.core.model.UserData
import kotlinx.coroutines.flow.Flow

class UserDataRepositoryImpl : UserDataRepository {

    override val userData: Flow<UserData>
        get() = TODO("Not yet implemented")

    override suspend fun setTheme(theme: Theme) {
        TODO("Not yet implemented")
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        TODO("Not yet implemented")
    }
    

}