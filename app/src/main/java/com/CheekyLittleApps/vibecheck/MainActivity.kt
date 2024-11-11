package com.CheekyLittleApps.vibecheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.CheekyLittleApps.vibecheck.data.MoodDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.MyApp
import com.CheekyLittleApps.vibecheck.ui.theme.VibeCheckTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(applicationContext, MoodDatabase::class.java, "mood_entries").build()
        lifecycleScope.launch(Dispatchers.IO) {
            val moods = db.moodDao().getAll()
            // Use withContext to update UI if needed
            withContext(Dispatchers.Main) {
                // Update your UI with the fetched data
            }
        val moodDao = db.moodDao()
        //val moodEntries: List<MoodEntry> = moodDao.getAll()
        setContent {
                    MyApp(moods)
        }
    }
}
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp()
//}