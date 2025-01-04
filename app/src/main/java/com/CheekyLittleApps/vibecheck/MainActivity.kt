package com.CheekyLittleApps.vibecheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.CheekyLittleApps.vibecheck.data.MoodDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.MyApp
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity()
{
    private val moodDatabase by lazy {MoodDatabase.getDatabase(this).moodDao()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(applicationContext, MoodDatabase::class.java, "mood_entries").build()
        val viewModel = MainViewModel(db.moodDao())
        lifecycleScope.launch(Dispatchers.IO) {
            val moods = db.moodDao().getAll()
            // Use withContext to update UI if needed
            val moodDao = db.moodDao()
            val moodEntries: Flow<List<MoodEntry>> = moodDao.getAll()
            withContext(Dispatchers.Main) {
                // Update your UI with the fetched data
                setContent {
                    MyApp(viewModel)
                }
            }
    }
}}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp()
//}