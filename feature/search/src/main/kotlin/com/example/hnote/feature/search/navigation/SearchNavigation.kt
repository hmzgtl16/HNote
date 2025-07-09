package com.example.hnote.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hnote.core.navigation.Route
import com.example.hnote.feature.search.SearchRoute

fun NavGraphBuilder.searchScreen() {
    composable<Route.Search> {
        SearchRoute()
    }
}