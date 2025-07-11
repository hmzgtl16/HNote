package com.example.hnote.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.hnote.core.model.RepeatMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Instant
import javax.inject.Inject
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

class AlarmSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(id: Long, scheduleTime: Instant, repeatMode: RepeatMode) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmScheduler.ALARM_EXTRA_ID, id)
            putExtra(AlarmScheduler.ALARM_EXTRA_ID, repeatMode.ordinal)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        )

        when (repeatMode) {
            RepeatMode.NONE -> {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    pendingIntent
                )
            }

            RepeatMode.DAILY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    1.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }

            RepeatMode.WEEKLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    7.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }

            RepeatMode.MONTHLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    30.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }

            RepeatMode.YEARLY -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.toEpochMilliseconds(),
                    365.days.toLong(DurationUnit.MILLISECONDS),
                    pendingIntent
                )
            }
        }
    }

    override fun cancel(id: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        )

        alarmManager.cancel(pendingIntent)
    }
}