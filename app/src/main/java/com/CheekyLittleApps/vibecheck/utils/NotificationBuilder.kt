package com.CheekyLittleApps.vibecheck.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import com.CheekyLittleApps.vibecheck.R

class NotificationBuilder(context: Context){
    var CHANNEL_ID = "mood_notification_channel"
    var NOTIFICATION_ID = 1

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Title")
        .setContentText("Text")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    fun createNotificationChannel(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Channel"
            val descriptionText = "Mood Notification Channel"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply { description = descriptionText  }

            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}
