package com.example.hnote.core.alarm

import com.example.hnote.core.model.RepeatMode
import kotlinx.datetime.Instant

interface AlarmScheduler {
    fun schedule(id: Long, scheduleTime: Instant, repeatMode: RepeatMode)
    fun cancel(id: Long)

    companion object {
        const val ALARM_EXTRA_ID = "ALARM_EXTRA_ID"
    }
}