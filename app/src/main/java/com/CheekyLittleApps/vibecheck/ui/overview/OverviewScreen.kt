package com.CheekyLittleApps.vibecheck.ui.overview

import android.icu.text.Transliterator.Position
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.Cards.MoodCard
import com.CheekyLittleApps.vibecheck.ui.DateRangePickerModal
import com.CheekyLittleApps.vibecheck.ui.SingleChoiceSegmentedButton
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: MainViewModel,
    onClickAddEntry: () -> Unit = {},
) {

    val moodEntries = viewModel.getAllMoodEntries().collectAsState(initial = emptyList())
    // MutableState to keep track of the input text
    var text by remember { mutableStateOf("") }
    // List to store user input
    var itemList by remember { mutableStateOf(listOf<String>()) }

    val calendar = Calendar.getInstance()
    val currentDay = Date()
    val dateRangePickerState = rememberDateRangePickerState()
    var startDate = dateRangePickerState.selectedStartDateMillis
    val endDate = dateRangePickerState.selectedEndDateMillis
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddEntry) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        // Column Layout for Text Input and List Display
        Column(modifier = Modifier.padding(innerPadding)) {
            // Text Field for user input
            BasicTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            SingleChoiceSegmentedButton()

            //Potentially used as general mood category
            //AssistChipExample()

            // Button to submit input and add to the list
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        // Add input text to the list and clear input field
                        itemList = itemList + text
                        val date = Calendar.getInstance().time
                        val currentDate = formatter.format(date)
                        val currentTime = System.currentTimeMillis()
                        var moodEntry = MoodEntry(date = currentDate, time = currentTime, mood = text)
                        viewModel.addMoodEntry(moodEntry)
                        text = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add")
            }

            Button(
                onClick = {
                    viewModel.nuke()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete Data")
            }

            var isClicked by remember { mutableStateOf(false) }

            Button(
                onClick = {
                    isClicked = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Date Picker")
            }

            if (isClicked)
            {
                DateRangePickerModal(onDateRangeSelected = ({ dateRange -> System.currentTimeMillis(); System.currentTimeMillis()}), { isClicked = false }, dateRangePickerState)
            }

            if(startDate != null && endDate != null)
            {
                Text(text = "Start Date: " + formatter.format(startDate + 1.days.inWholeMilliseconds) + " End Date: " + formatter.format(endDate + 1.days.inWholeMilliseconds))
            }
            else
            {
                Text(text = "Start Date: " + formatter.format(Calendar.getInstance().time) + " End Date: " + formatter.format(Calendar.getInstance().time))
            }

            moodEntries.value.forEach { entry ->
                if(startDate != null && endDate != null)
                {
                    //Start of implementing Calendar over the deprecated Date class
                    val startDay = Calendar.getInstance()
                    startDay.setTimeInMillis(startDate!! + 1.days.inWholeMilliseconds)
                    val endDay = Calendar.getInstance()
                    endDay.setTimeInMillis(endDate!! + 1.days.inWholeMilliseconds)
                    val entryDay = Calendar.getInstance()
                    entryDay.setTimeInMillis(entry.time)

                    startDay.set(Calendar.HOUR_OF_DAY, 0)
                    startDay.set(Calendar.MINUTE, 0)
                    startDay.set(Calendar.MILLISECOND, 0)
                    endDay.set(Calendar.HOUR_OF_DAY, 23)
                    endDay.set(Calendar.MINUTE, 59)
                    endDay.set(Calendar.SECOND, 59)

                    //Shows date range selected for debugging
                    //Text(text = "Start Date: " + startDate + " End Date: " + endDate)

                    if((entryDay.after(startDay) && entryDay.before(endDay)) || entryDay.equals(startDay) || entryDay.equals(startDay))
                    {
                        MoodCard("PLACEHOLDER_MOOD_CATEGORY", entry.mood, entry.date)
                    }
                } else
                {
                    MoodCard("PLACEHOLDER_MOOD_CATEGORY", entry.mood, entry.date)
                }
            }
        }
    }



}


// May be used for selecting general mood categories
//@Composable
//fun AssistChipExample() {
//    AssistChip(
//        onClick = { Log.d("Assist chip", "hello world") },
//        label = { Text("Assist chip") },
//        leadingIcon = {
//            Icon(
//                Icons.Filled.Settings,
//                contentDescription = "Localized description",
//                Modifier.size(AssistChipDefaults.IconSize)
//            )
//        }
//    )
//}
