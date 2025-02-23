package com.CheekyLittleApps.vibecheck.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import kotlinx.coroutines.flow.Flow

class MoodRepository(private val moodDao: MoodDao)
{
    val allMoods: Flow<List<MoodEntry>> = moodDao.getAll()

    fun getMood(uId: Int): Flow<MoodEntry> {
        return moodDao.getMood(uId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(moodEntry: MoodEntry)
    {
        moodDao.insertAll(moodEntry)
    }
}