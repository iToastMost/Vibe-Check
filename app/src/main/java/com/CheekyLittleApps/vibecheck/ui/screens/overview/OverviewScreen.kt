package com.CheekyLittleApps.vibecheck.ui.screens.overview

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.CheekyLittleApps.vibecheck.ui.cards.MoodCard
import com.CheekyLittleApps.vibecheck.ui.DateRangePickerModal
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.days
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.ui.TimePickerModal
import com.CheekyLittleApps.vibecheck.ui.alerts.SimpleAlertDialog


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: MainViewModel,
    onClickAddEntry: () -> Unit = {},
    //onClickViewMood: (String) -> Unit = {},
    onClickViewMood: (Int) -> Unit = {},
    onClickSendNotification: () -> Unit,
    onClickAlarm: () -> Unit,
    context: Context
) {
    var expanded by remember { mutableStateOf(false) }
    val moodEntries = viewModel.getAllMoodEntries().collectAsState(initial = emptyList())
    // MutableState to keep track of the input text
    // List to store user input
    var itemList by remember { mutableStateOf(listOf<String>()) }

//    val alarmScheduler : AlarmScheduler = AlarmSchedulerHelper(context)
//    val alarmItem : AlarmItem? = null

    val calendar = Calendar.getInstance()
    val currentDay = Date()
    val dateRangePickerState = rememberDateRangePickerState()
    var startDate = dateRangePickerState.selectedStartDateMillis
    val endDate = dateRangePickerState.selectedEndDateMillis
    val formatter = SimpleDateFormat("EEE, MMM d, yyyy")
    var isClicked by remember { mutableStateOf(false) }
    var isDeleteClicked by remember { mutableStateOf(false) }
    var isSettingsClicked by remember { mutableStateOf(false) }
    var isExportClicked by remember { mutableStateOf(false) }
    var isTimePickerClicked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center,
//                        text = "Vibe Check",
//                    )
                },
                navigationIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.Menu, contentDescription = "Dropdown Menu")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Delete Data") },
                            leadingIcon = { Icon(Icons.Outlined.Delete, contentDescription = "Delete All Data Button")},
                            onClick = {
                                isDeleteClicked = true
                                expanded = false
                                      },
                        )

                        DropdownMenuItem(
                            text = { Text("Settings")},
                            leadingIcon = {Icon(Icons.Outlined.Settings, contentDescription = "App settings button")},
                            onClick = {
                                isSettingsClicked = true
                                expanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Export to CSV")},
                            leadingIcon = {Icon(Icons.Outlined.Share, contentDescription = "Export to CSV button")},
                            onClick = {
                                isExportClicked = true
                                expanded = false
                            }
                        )
                    }
                },
                actions = {

//                    IconButton(onClick = {
//                        onClickSendNotification()
//                        isTimePickerClicked = true
//                        onClickAlarm()
//                    }){
//                        Icon(Icons.Default.Notifications, contentDescription = "Send notification button")
//                    }

//                    IconButton(onClick = { }) {
//                        Icon(Icons.Default.Star, contentDescription = "Filter Options")
//                    }

                    IconButton(onClick = {
                        isClicked = true
                    }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Date Range Picker")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    text = "Bottom app bar",
//                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddEntry) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        // Column Layout for List Display
        Column(modifier = Modifier.padding(innerPadding)) {

            if (isClicked)
            {
                DateRangePickerModal(onDateRangeSelected = ({ dateRange -> System.currentTimeMillis(); System.currentTimeMillis()}), { isClicked = false }, dateRangePickerState)
            }

            if(isDeleteClicked){
                SimpleAlertDialog(viewModel, { isDeleteClicked = false },
                    "This will delete all of your data and there is no way to retrieve it. Are you sure you want to do this?",
                    { viewModel.nuke() })
            }

            if(isSettingsClicked){
                SimpleAlertDialog(viewModel, { isSettingsClicked = false },
                    "Settings will be added in a future update when there are more options like themes.",
                    {  })
            }

            if(isExportClicked){
                SimpleAlertDialog(viewModel, { isExportClicked = false },
                    "Export to CSV is a feature currently being worked on. Thank you for your patience.",
                    {  })
            }

            if(isTimePickerClicked){
                TimePickerModal( {isTimePickerClicked = false}, {isTimePickerClicked = false}, context)
            }

            if(startDate != null && endDate != null)
            {
                Text(text = "Start Date: " + formatter.format(startDate + 1.days.inWholeMilliseconds) + " End Date: " + formatter.format(endDate + 1.days.inWholeMilliseconds))
            }
            else
            {
                Text(text = "Start Date: " + formatter.format(Calendar.getInstance().time) + " End Date: " + formatter.format(Calendar.getInstance().time))
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                //contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(moodEntries.value) { entry ->
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
                            MoodCard(entry.currentMood, entry.mood, entry.date,
                                entry.uid, { onClickViewMood(entry.uid) })
                        }
                    } else
                    {
                        MoodCard(entry.currentMood, entry.mood, entry.date,
                            entry.uid, { onClickViewMood(entry.uid) })
                    }
                }
            }
        }
    }
}

