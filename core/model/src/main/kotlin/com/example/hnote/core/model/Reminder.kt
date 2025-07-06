package com.example.hnote.core.model

import kotlinx.datetime.Instant

data class Reminder(
    val time: Instant,
    val repeatMode: ReminderRepeatMode = ReminderRepeatMode.NONE,
    val completed: Boolean = false
)
