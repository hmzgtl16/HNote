package com.example.hnote.core.ui

import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.model.ReminderRepeatMode

fun ReminderRepeatMode.icon() = when (this) {
    ReminderRepeatMode.NONE -> AppIcons.AlarmOnce
    else -> AppIcons.AlarmRepeat
}