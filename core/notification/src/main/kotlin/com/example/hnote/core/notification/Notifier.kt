package com.example.hnote.core.notification

import com.example.hnote.core.model.Reminder

interface Notifier {
    fun postReminderNotification(reminder: Reminder)
}