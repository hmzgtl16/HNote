package com.example.hnote.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnote.feature.search.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(route = SearchRoute, navOptions = navOptions)
}

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    onNoteClick: (Long) -> Unit
) {
    composable<SearchRoute> {
        SearchRoute(
            onBackClick = onBackClick,
            onNoteClick = onNoteClick
        )
    }

}