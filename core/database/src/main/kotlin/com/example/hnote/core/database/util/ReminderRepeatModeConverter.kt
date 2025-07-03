package com.example.hnote.core.database.util

import androidx.room.TypeConverter

internal class ReminderRepeatModeConverter {
    @TypeConverter
    fun intToReminderRepeatMode(value: Int?): ReminderRepeatMode? =
        value?.let(Int::asReminderRepeatMode)

    @TypeConverter
    fun repeatModeToInt(reminderRepeatMode: ReminderRepeatMode?): Int? =
        reminderRepeatMode?.let(ReminderRepeatMode::ordinal)
}