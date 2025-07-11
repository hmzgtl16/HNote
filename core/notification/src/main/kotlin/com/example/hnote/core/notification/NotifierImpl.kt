package com.example.hnote.core.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hnote.core.model.Reminder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : Notifier {
    override fun postReminderNotification(reminder: Reminder) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val deepLink = deepLink(id = reminder.id)
        val notification = context.createNotification(deepLink = deepLink)
        NotificationManagerCompat
            .from(context)
            .notify(reminder.id.toInt(), notification)
    }
}