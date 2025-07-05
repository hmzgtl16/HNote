package com.example.hnote.core.model

import kotlinx.datetime.Instant

data class Reminder(
    val reminder: Instant,
    val repeatMode: ReminderRepeatMode = ReminderRepeatMode.NONE,
    val completed: Boolean = false
)
