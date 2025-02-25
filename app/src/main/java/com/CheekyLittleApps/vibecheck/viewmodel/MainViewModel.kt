package com.CheekyLittleApps.vibecheck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.CheekyLittleApps.vibecheck.data.MoodDao
import com.CheekyLittleApps.vibecheck.data.MoodRepository
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val dao: MoodDao) : ViewModel()
{
    fun addMoodEntry(moodEntry: MoodEntry)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            dao.insertAll(moodEntry)
        }
    }

    fun getAllMoodEntries() = dao.getAll()

    fun getMoodEntryById(roomId: Int): Flow<MoodEntry?> {
        return dao.getMood(roomId)
    }

    fun updateMoodEntry(moodEntry: MoodEntry){
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateMood(moodEntry)
        }
    }

    fun nuke()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            dao.nukeTable()
        }
    }
}