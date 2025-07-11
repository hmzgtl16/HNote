package com.example.hnote.core.notification

interface Notifier {
    fun postReminderNotification(id: Long)
}