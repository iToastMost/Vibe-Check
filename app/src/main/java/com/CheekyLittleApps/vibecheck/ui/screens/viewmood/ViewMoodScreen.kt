package com.CheekyLittleApps.vibecheck.ui.screens.viewmood

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CheekyLittleApps.vibecheck.data.MoodEmoji
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.alerts.SimpleAlertDialog
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
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
    var moodList by remember { mutableStateOf(setOf<String>()) }
    var text by remember { mutableStateOf("") }
    var isDeleteClicked by remember { mutableStateOf(false) }
    val emojiSelectionSize = 1
    val emojiFontSize = 30.sp

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
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
                        isDeleteClicked = true
                    }){
                        Icon(Icons.Default.Delete, contentDescription = "Delete Button for Mood Entry")
                    }

                    IconButton(onClick = {
                        if (text.isNotBlank()) {
                            // Add input text to the list and clear input field
                            mood?.let {
                                it.mood = text
                                it.currentMood = moodList.joinToString(", ")
                                viewModel.updateMoodEntry(it)
                            }
                            text = ""
                            onClickEntryAdded()
                        }
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "Confirm Button")
                    }
                }
            )
        },
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            if(isDeleteClicked){
                SimpleAlertDialog(viewModel, { isDeleteClicked = false },
                    "This will delete your entry permanently. Are you sure you want to do this?",
                    { mood?.let { viewModel.deleteMoodEntry(it)
                    onClickEntryAdded()} })
            }

            LaunchedEffect(key1 = mood) {
                mood?.let {
                    text = it.mood
                    moodsPicked = it.currentMood
                    moodList = it.currentMood.split(", ").toSet()
                }
            }
        }

        Column(modifier = Modifier.padding(innerPadding)){

            FlowRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                moods.forEach { option ->
                    val selected = moodList.contains(option.emoji)

                    //May be used for selecting general mood categories
                    FilterChip(
                        onClick = {
                            if(moodList.size < emojiSelectionSize) {
                                moodList = if (selected){
                                    moodList - option.emoji
                                } else{
                                    moodList + option.emoji
                                }
                            } else{
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
        }
    }

}