package com.example.hnote.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class SearchQuery(
    val query: String,
    val queried: Instant = Clock.System.now()
)
