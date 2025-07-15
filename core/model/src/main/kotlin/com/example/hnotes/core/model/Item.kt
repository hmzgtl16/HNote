package com.example.hnotes.core.model

data class Item(
    val id: Long = 0L,
    val content: String = "",
    val checked: Boolean = false,
)
