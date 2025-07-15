package com.example.hnotes.core.model

import kotlinx.datetime.Instant

data class Reminder(
    val id: Long = 0L,
    val time: Instant,
    val repeatMode: RepeatMode = RepeatMode.NONE
)
