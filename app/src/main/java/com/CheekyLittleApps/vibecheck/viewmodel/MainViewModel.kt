package com.CheekyLittleApps.vibecheck.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CheekyLittleApps.vibecheck.data.MoodDao
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun nuke()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            dao.nukeTable()
        }
    }
}