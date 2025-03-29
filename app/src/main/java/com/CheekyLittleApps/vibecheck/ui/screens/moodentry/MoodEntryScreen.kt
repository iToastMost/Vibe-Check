package com.CheekyLittleApps.vibecheck.ui.screens.moodentry

import android.annotation.SuppressLint
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CheekyLittleApps.vibecheck.data.MoodEmoji
import com.CheekyLittleApps.vibecheck.data.unicodeToString
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.SingleChoiceSegmentedButton
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.exp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MoodEntryScreen(
    viewModel: MainViewModel,
    onClickEntryAdded: () -> Unit = {},
){
    var text by remember { mutableStateOf("") }
    var numberSelected = 0
    //var expanded by remember { mutableStateOf(false) }
    var moodsPicked by remember { mutableStateOf("") }
    var moodList by remember { mutableStateOf(setOf<String>()) }
    val emojiSelectionSize = 1
    val emojiFontSize = 30.sp

    val formatter = SimpleDateFormat("EEE, MMM d, yyyy KK:mm:aaa")
    val moods = enumValues<MoodEmoji>()


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = {
                        onClickEntryAdded()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Cancel Button")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (text.isNotBlank()) {
                            // Add input text to the list and clear input field
                            val date = Calendar.getInstance().time
                            val currentDate = formatter.format(date)
                            val currentTime = System.currentTimeMillis()
                            var moodEntry = MoodEntry(date = currentDate, time = currentTime, mood = text, currentMood = moodList.joinToString(", "))
                            //var moodEntry = MoodEntry(date = currentDate, time = currentTime, mood = text, currentMood = moodList.joinToString(", "))
                            viewModel.addMoodEntry(moodEntry)
                            text = ""
                            onClickEntryAdded()
                        }
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "Confirm Button")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            FlowRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                moods.forEach { option ->
                    val selected = moodList.contains(option.emoji)
                    //May be used for selecting general mood categories
                    FilterChip(
                        onClick = {
                            if(moodList.size < emojiSelectionSize){
                                moodList = if (selected) {
                                    moodList - option.emoji
                                }
                                else{
                                    moodList + option.emoji
                                }
                            } else {
                                moodList = moodList - option.emoji
                            }
                        },
                        label = { Text(option.emoji, fontSize = emojiFontSize) },
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
                label = { Text("How are you feeling?") },
                onValueChange = { newText -> text = newText },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 8.dp)
            )
        }
    }
}