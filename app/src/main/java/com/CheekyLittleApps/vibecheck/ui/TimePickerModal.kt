package com.CheekyLittleApps.vibecheck.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    var showDialog by remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance()

    val timePickerSate = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    if(showDialog){
        Dialog(
            onDismissRequest = {showDialog = false}
        ) {
            Column {
                TimePicker(
                    state = timePickerSate
                )

                Button(onClick = onDismiss) {
                    Text(text = "Cancel")
                }

                Button(onClick = onConfirm) {
                    Text(text = "Confirm")
                }
            }
        }
    }



}