package com.example.hnotes.core.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {
    private val _events = MutableSharedFlow<NavigationEvent>()
    override val events: SharedFlow<NavigationEvent> = _events.asSharedFlow()

    override suspend fun navigateTo(route: Route, navOptions: NavOptions?) {
        _events.emit(
            value = NavigationEvent.NavigateTo(
                route = route,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateBack() {
        _events.emit(value = NavigationEvent.NavigateBack)
    }
}