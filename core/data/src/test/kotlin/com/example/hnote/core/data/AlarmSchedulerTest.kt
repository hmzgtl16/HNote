package com.example.hnote.core.data

import com.example.hnote.core.alarm.AlarmScheduler
import com.example.hnote.core.model.RepeatMode
import kotlinx.datetime.Instant

class AlarmSchedulerTest : AlarmScheduler {
    override fun schedule(
        id: Long,
        scheduleTime: Instant,
        repeatMode: RepeatMode
    ) {
        TODO("Not yet implemented")
    }

    override fun cancel(id: Long) {
        TODO("Not yet implemented")
    }

}
