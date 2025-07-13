package com.example.hnote.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.hnote.core.navigation.DEEP_LINK_SCHEME_AND_HOST


private const val NOTIFICATION_CHANNEL_ID = "hnote_notification_channel"
private const val NOTIFICATION_REQUEST_CODE = 0
private const val TARGET_ACTIVITY_NAME = "com.example.hnote.MainActivity"

fun deepLink(id: Long): Uri = "$DEEP_LINK_SCHEME_AND_HOST/$id".toUri()

fun Context.createNotification(deepLink: Uri): Notification {
    ensureNotificationChannelExists()
    return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(getString(R.string.core_notification_notification_title))
            setContentText(getString(R.string.core_notification_notification_description))
            setContentIntent(taskPendingIntent(deepLink = deepLink))
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setAutoCancel(true)
        }
        .build()
}

private fun Context.ensureNotificationChannelExists() {

    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        getString(R.string.core_notification_channel_name),
        NotificationManager.IMPORTANCE_DEFAULT,
    ).apply {
        description = getString(R.string.core_notification_channel_description)
    }

    NotificationManagerCompat
        .from(this)
        .createNotificationChannel(channel)
}

private fun Context.taskPendingIntent(deepLink: Uri): PendingIntent? =
    PendingIntent.getActivity(
        this,
        NOTIFICATION_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = deepLink
            component = ComponentName(packageName, TARGET_ACTIVITY_NAME)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )