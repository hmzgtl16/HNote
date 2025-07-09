package com.example.hnote.core.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.SharedFlow

interface Navigator {

    val events: SharedFlow<NavigationEvent>

    suspend fun navigateTo(route: Route, navOptions: NavOptions? = null)
    suspend fun navigateBack()
}