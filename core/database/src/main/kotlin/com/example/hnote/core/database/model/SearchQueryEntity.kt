package com.example.hnote.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Entity(tableName = "search_queries")
data class SearchQueryEntity(
    @PrimaryKey
    val text: String,
    val queried: Instant
)
