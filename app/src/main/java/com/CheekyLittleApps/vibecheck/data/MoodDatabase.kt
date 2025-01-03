package com.CheekyLittleApps.vibecheck.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry

@Database(entities = [MoodEntry::class], version = 1, exportSchema = true)
abstract class MoodDatabase : RoomDatabase()
{
    abstract fun moodDao(): MoodDao

    companion object
    {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase
        {
            if(INSTANCE == null)
            {
                synchronized(this)
                {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): MoodDatabase
        {
            return Room.databaseBuilder(context.applicationContext,
                MoodDatabase::class.java,
                "mood_entries").build()
        }
    }
}