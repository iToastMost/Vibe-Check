package com.CheekyLittleApps.vibecheck.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.CheekyLittleApps.vibecheck.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        val channelId = "alarm_id"
        context?.let { ctx ->
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = "vibecheck://vibe_destination".toUri()

            val pendingIntent = TaskStackBuilder.create(ctx).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(1234, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }

            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val builder = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Vibe Checkin In")
                .setContentText("How are we feeling today?")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            notificationManager.notify(1, builder.build())
        }
    }

}