package com.CheekyLittleApps.vibecheck.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel

@Composable
fun AlarmScreen(
    viewModel: MainViewModel,
    onClickAlarm: () -> Unit
) {
    Scaffold(
        topBar = {},

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "this is the alarm screen")
        }

    }
}