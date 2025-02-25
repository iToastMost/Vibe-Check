package com.CheekyLittleApps.vibecheck.ui.viewmood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.data.MoodColor
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ViewMoodScreen(
    viewModel: MainViewModel,
    roomId: Int,
    onClickEntryAdded: () -> Unit = {}
) {
    val mood by viewModel.getMoodEntryById(roomId).collectAsState(initial = null)
    var moodGeneral: String
    //var expanded by remember { mutableStateOf(false) }
    var moodsPicked by remember { mutableStateOf("") }
    val moodList = mutableListOf<String>()
    var text by remember { mutableStateOf("") }

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val moods = enumValues<MoodColor>()

    Column(modifier = Modifier.padding(32.dp)) {

        LaunchedEffect(key1 = mood) {
            mood?.let {
                text = it.mood
                moodGeneral = it.currentMood
            }
        }
        }




    Column(modifier = Modifier.padding(32.dp)){

        FlowRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            moods.forEach { option ->
                var selected by remember { mutableStateOf(false) }
                //May be used for selecting general mood categories
                FilterChip(
                    onClick = {
                        selected = !selected
                        if(selected)
                            moodsPicked = option.toString()
                        moodList.add(option.toString())
                    },
                    label = { Text(option.toString()) },
                    selected = selected,
                    leadingIcon = if (selected) {
                        {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Localized description",
                                Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }

        // Text Field for user input
        TextField(
            value = text,
            label = {Text("How are you feeling?")},
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 8.dp)
        )

        // Button to submit input and add to the list
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    // Add input text to the list and clear input field
                    mood?.let {
                        it.mood = text
                        it.currentMood = moodsPicked
                        viewModel.updateMoodEntry(it)
                    }
                    text = ""
                    onClickEntryAdded()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        ) {
            Text("Add")
        }
    }
}