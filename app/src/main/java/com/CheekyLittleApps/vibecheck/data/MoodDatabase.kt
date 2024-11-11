package com.CheekyLittleApps.vibecheck.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry

@Database(entities = [MoodEntry::class], version = 1)
abstract class MoodDatabase : RoomDatabase()
{
    abstract fun moodDao(): MoodDao
}