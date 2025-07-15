package com.example.hnotes.core.navigation

import androidx.navigation.NavOptions

interface NavigationEvent {
    data class NavigateTo(
        val route: Route,
        val navOptions: NavOptions? = null
    ) : NavigationEvent

    data object NavigateBack : NavigationEvent
}
