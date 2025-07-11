package com.example.hnote.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.hnote.core.notification.Notifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notifier: Notifier
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(AlarmScheduler.ALARM_EXTRA_ID, 0L)
        notifier.postReminderNotification(id = id)
    }
}