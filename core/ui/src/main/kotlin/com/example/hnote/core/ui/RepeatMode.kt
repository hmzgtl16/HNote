package com.example.hnote.core.ui

import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.model.RepeatMode

fun RepeatMode.icon() = when (this) {
    RepeatMode.NONE -> AppIcons.AlarmOnce
    else -> AppIcons.AlarmRepeat
}