package com.CheekyLittleApps.vibecheck.ui.screens.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    viewModel: MainViewModel,
    onClickAlarm: () -> Unit,
    onClickEntryAdded: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Vibe Check",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onClickEntryAdded()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Cancel Button")
                    }
                },
                actions = {

//                    IconButton(onClick = {
//                        //onClickSendNotification()
//                        //isTimePickerClicked = true
//                        onClickAlarm()
//                    }){
//                        Icon(Icons.Default.Notifications, contentDescription = "Send notification button")
//                    }
//
//                    IconButton(onClick = {
//                        isClicked = true
//                    }) {
//                        Icon(Icons.Default.DateRange, contentDescription = "Date Range Picker")
//                    }
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
//            FloatingActionButton(onClick = onClickAddEntry) {
//                Icon(Icons.Default.Add, contentDescription = "Add")
//            }
        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "this is the alarm screen")
        }

    }
}