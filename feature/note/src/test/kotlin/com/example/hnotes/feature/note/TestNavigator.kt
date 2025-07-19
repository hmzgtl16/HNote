package com.example.hnotes.feature.note

import androidx.navigation.NavOptions
import com.example.hnotes.core.navigation.NavigationEvent
import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.core.navigation.Route
import kotlinx.coroutines.flow.SharedFlow

class TestNavigator : Navigator {
    override val events: SharedFlow<NavigationEvent>
        get() = TODO("Not yet implemented")

    override suspend fun navigateTo(
        route: Route,
        navOptions: NavOptions?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun navigateBack() {
        TODO("Not yet implemented")
    }
}
