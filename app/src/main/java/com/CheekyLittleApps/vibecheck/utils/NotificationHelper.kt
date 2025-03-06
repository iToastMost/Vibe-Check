package com.CheekyLittleApps.vibecheck.utils

import android.Manifest
import android.app.Activity.NOTIFICATION_SERVICE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import com.CheekyLittleApps.vibecheck.MainActivity
import com.CheekyLittleApps.vibecheck.R

class NotificationHelper(context: Context, activity: MainActivity) {

    val context = context
    val activity = activity

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun sendNotification(){
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = "vibecheck://vibe_destination".toUri()

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1234, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Vibe Checkin In")
            .setContentText("How are we feeling today?")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)){
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101)
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }

    }

    companion object{
        const val CHANNEL_ID = "channel"
        const val NOTIFICATION_ID = 1234
        const val DESCRIPTION = "Test notification"
    }

}
