package com.CheekyLittleApps.vibecheck.data

import androidx.annotation.WorkerThread
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import kotlinx.coroutines.flow.Flow

class MoodRepository(private val moodDao: MoodDao)
{
    val allMoods: Flow<List<MoodEntry>> = moodDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(moodEntry: MoodEntry)
    {
        moodDao.insertAll(moodEntry)
    }
}