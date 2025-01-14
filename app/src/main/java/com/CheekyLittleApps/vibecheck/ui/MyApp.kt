package com.CheekyLittleApps.vibecheck.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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

        HorizontalDivider()

        moodEntries.value.forEach { entry ->
            if(startDate != null && endDate != null)
            {
                val sD = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(startDate!! + 1.days.inWholeMilliseconds)
                val eD = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(endDate!! + 2.days.inWholeMilliseconds)
                val cD = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(entry.time)
                val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(cD)
                val dateStart = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(sD)
                val cDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(cD)
                dateStart.setHours(0)
                dateStart.setMinutes(0)
                date.setHours(23)
                date.setMinutes(59)
                val dateRange = dateStart..date

                Text(text = "Start Date: " + sD + " End Date: " + eD)
                if(cDate in dateRange || cD == sD || cD == eD)
                {
                    Text(text = entry.date + ":\n" + entry.mood)
                    HorizontalDivider()
                }
            }
            else
            {
                Text(text = entry.date + ":\n" + entry.mood)
                HorizontalDivider()
            }
        }
    }

}
