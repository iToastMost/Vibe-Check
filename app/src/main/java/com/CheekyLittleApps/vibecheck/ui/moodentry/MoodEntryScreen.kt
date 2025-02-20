package com.CheekyLittleApps.vibecheck.ui.moodentry

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.data.MoodColor
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.SingleChoiceSegmentedButton
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.exp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodEntryScreen(
    viewModel: MainViewModel,
    onClickEntryAdded: () -> Unit = {},
){
    var text by remember { mutableStateOf("") }
    //var expanded by remember { mutableStateOf(false) }
    var moodPicked by remember { mutableStateOf("") }

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val moods = enumValues<MoodColor>()

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
                            moodPicked = option.toString()
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

        //potential way to select mood with dropdown menus
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ){
//            Text("Selected Mood: " + moodPicked)
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End
//            ){
//                IconButton(onClick = { expanded = !expanded }) {
//                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Your Mood")
//                }
//
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//                ) {
//                    moods.forEach { option ->
//                        DropdownMenuItem(
//                            text = { Text(option.toString().lowercase()) },
//                            onClick = {
//                                expanded = !expanded
//                                moodPicked = option.toString()
//                            }
//                        )
//                    }
//                }
//
//            }
//        }


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
                    val date = Calendar.getInstance().time
                    val currentDate = formatter.format(date)
                    val currentTime = System.currentTimeMillis()
                    var moodEntry = MoodEntry(date = currentDate, time = currentTime, mood = text, currentMood = moodPicked)
                    viewModel.addMoodEntry(moodEntry)
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