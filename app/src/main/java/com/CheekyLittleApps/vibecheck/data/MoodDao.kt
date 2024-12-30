package com.CheekyLittleApps.vibecheck.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import com.CheekyLittleApps.vibecheck.model.MoodEntry

@Entity
@Dao
interface MoodDao
{
    @Query("SELECT * FROM mood_entries")
    fun getAll(): List<MoodEntry>

    @Insert
    suspend fun insertAll(vararg moodEntries: MoodEntry)

    @Delete
    suspend fun delete(moodEntry: MoodEntry)

    //add additional such as find by date
}