package com.CheekyLittleApps.vibecheck.ui.moodentry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.SingleChoiceSegmentedButton
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun MoodEntryScreen(
    viewModel: MainViewModel,
    onClickEntryAdded: () -> Unit = {},
){
    var text by remember { mutableStateOf("") }
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")


    
    Column(modifier = Modifier.padding(32.dp)){

        SingleChoiceSegmentedButton()

        // Text Field for user input
        TextField(
            value = text,
            label = {Text("How are you feeling?")},
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Button to submit input and add to the list
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    // Add input text to the list and clear input field
                    val date = Calendar.getInstance().time
                    val currentDate = formatter.format(date)
                    val currentTime = System.currentTimeMillis()
                    var moodEntry = MoodEntry(date = currentDate, time = currentTime, mood = text)
                    viewModel.addMoodEntry(moodEntry)
                    text = ""
                    onClickEntryAdded()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Add")
        }
    }
}