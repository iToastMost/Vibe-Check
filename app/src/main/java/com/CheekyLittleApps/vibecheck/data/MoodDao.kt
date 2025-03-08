package com.CheekyLittleApps.vibecheck.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import kotlinx.coroutines.flow.Flow

@Entity
@Dao
interface MoodDao
{
    @Query("SELECT * FROM mood_entries")
    fun getAll(): Flow<List<MoodEntry>>

    @Query("SELECT * FROM mood_entries WHERE uid = :id")
    fun getMood(id: Int): Flow<MoodEntry>

    @Insert
    suspend fun insertAll(vararg moodEntries: MoodEntry)

    @Update
    fun updateMood(vararg moodEntries: MoodEntry)


    @Delete
    fun deleteMoodEntry(vararg moodEntries: MoodEntry)

    @Query("DELETE FROM mood_entries")
    fun nukeTable()
    //add additional such as find by date
}