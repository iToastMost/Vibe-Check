package com.CheekyLittleApps.vibecheck.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.CheekyLittleApps.vibecheck.model.MoodEntry

@Dao
interface MoodDao
{
    @Query("SELECT * FROM mood_entries")
    suspend fun getAll(): List<MoodEntry>

    @Insert
    suspend fun insertAll(vararg moodEntries: MoodEntry)

    @Delete
    suspend fun delete(moodEntry: MoodEntry)

    //add additional such as find by date
}