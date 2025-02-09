package com.CheekyLittleApps.vibecheck.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.data.MoodDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.Cards.MoodCard
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import com.CheekyLittleApps.vibecheck.utils.Converters
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(viewModel: MainViewModel) {

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

    // Column Layout for Text Input and List Display
    Column(modifier = Modifier.padding(50.dp)) {
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
