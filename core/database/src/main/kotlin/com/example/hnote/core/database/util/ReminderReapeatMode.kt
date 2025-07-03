package com.example.hnote.core.database.util

enum class ReminderRepeatMode {
    NONE,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

fun Int.asReminderRepeatMode(): ReminderRepeatMode =
    ReminderRepeatMode.entries.firstOrNull { it.ordinal == this } ?: ReminderRepeatMode.NONE