package com.example.hnotes.core.ui

import androidx.annotation.StringRes
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.model.RepeatMode

fun RepeatMode.icon() = when (this) {
    RepeatMode.NONE -> AppIcons.AlarmOnce
    else -> AppIcons.AlarmRepeat
}

@StringRes
fun RepeatMode.id(): Int = when (this) {
    RepeatMode.NONE -> R.string.core_ui_repeat_mode_none
    RepeatMode.DAILY -> R.string.core_ui_repeat_mode_daily
    RepeatMode.WEEKLY -> R.string.core_ui_repeat_mode_weekly
    RepeatMode.MONTHLY -> R.string.core_ui_repeat_mode_monthly
    RepeatMode.YEARLY -> R.string.core_ui_repeat_mode_yearly
}