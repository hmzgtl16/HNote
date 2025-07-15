package com.example.hnotes.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hnotes.core.navigation.Route
import com.example.hnotes.feature.search.SearchRoute

fun NavGraphBuilder.searchScreen() {
    composable<Route.Search> {
        SearchRoute()
    }
}