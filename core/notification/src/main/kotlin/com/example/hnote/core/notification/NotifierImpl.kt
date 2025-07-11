package com.example.hnote.core.notification

import android.content.Context
import com.example.hnote.core.model.Reminder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : Notifier {
    override fun postReminderNotification(reminder: Reminder) {

    }
}