package com.CheekyLittleApps.vibecheck.utils

import com.CheekyLittleApps.vibecheck.data.AlarmItem

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}