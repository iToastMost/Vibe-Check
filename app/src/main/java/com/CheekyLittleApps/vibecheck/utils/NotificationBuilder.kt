package com.CheekyLittleApps.vibecheck.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import com.CheekyLittleApps.vibecheck.R



fun createNotificationChannel(){
//    var builder = NotificationCompat.Builder(this, CHANNEL_ID)
//        .setSmallIcon(R.drawable.notification_icon)
//        .setContentTitle
//
//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//        val name = getString(R.string.channel_name)
//        val descriptionText = getString(R.string.channel_description)
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
//        mChannel.description = descriptionText
//
//        val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(mChannel)
//    }
}