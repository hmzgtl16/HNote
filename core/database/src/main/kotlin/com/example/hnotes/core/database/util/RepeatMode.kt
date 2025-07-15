package com.example.hnotes.core.database.util

enum class RepeatMode {
    NONE,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

fun Int.asRepeatMode(): RepeatMode =
    RepeatMode.entries.firstOrNull { it.ordinal == this } ?: RepeatMode.NONE