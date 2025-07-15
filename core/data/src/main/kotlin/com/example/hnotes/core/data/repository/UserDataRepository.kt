package com.example.hnotes.core.data.repository

import com.example.hnotes.core.model.Theme
import com.example.hnotes.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setTheme(theme: Theme)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}