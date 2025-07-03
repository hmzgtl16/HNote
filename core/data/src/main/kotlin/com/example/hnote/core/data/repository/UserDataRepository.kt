package com.example.hnote.core.data.repository

import com.example.hnote.core.model.Theme
import com.example.hnote.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setTheme(theme: Theme)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}