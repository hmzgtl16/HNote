package com.example.hnote.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "search_queries")
data class SearchQueryEntity(
    @PrimaryKey
    val text: String,
    val queried: Instant
)
