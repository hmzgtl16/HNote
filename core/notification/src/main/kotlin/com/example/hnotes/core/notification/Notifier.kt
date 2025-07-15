package com.example.hnotes.core.notification

interface Notifier {
    fun postReminderNotification(id: Long)
}