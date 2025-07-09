package com.example.hnote.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Notes : Route

    @Serializable
    data class Note(val noteId: Long?) : Route

    @Serializable
    data object Search : Route

    @Serializable
    data object Settings : Route
}
