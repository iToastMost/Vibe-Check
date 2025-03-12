package com.CheekyLittleApps.vibecheck.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.CheekyLittleApps.vibecheck.data.AlarmItem
import com.CheekyLittleApps.vibecheck.utils.AlarmScheduler
import com.CheekyLittleApps.vibecheck.utils.AlarmSchedulerHelper
import java.time.LocalDateTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    context: Context
){
    var showDialog by remember { mutableStateOf(true) }
    val currentTime = Calendar.getInstance()

    val alarmScheduler : AlarmScheduler = AlarmSchedulerHelper(context)
    var alarmItem : AlarmItem? = null

    val timePickerSate = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    if(showDialog){

        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = "NOTIFICATION SETTINGS NOT FINISHED")
                    Text(text = "COMING SOON")

                    TimePicker(
                        state = timePickerSate
                    )

                    Box(Modifier.fillMaxWidth()){
                        TextButton(
                            onClick = {
                                onDismiss()
                                alarmItem?.let (alarmScheduler::cancel)
                            },
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Text(text = "Cancel")
                        }

                        TextButton(
                            onClick = {
                                onDismiss()
                                //onClickConfirm()
                                alarmItem = AlarmItem(
                                    alarmTime = LocalDateTime.now().plusSeconds(10), "message"
                                )
                                alarmItem?.let (alarmScheduler::schedule)
                            },
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(text = "Confirm")
                        }
                }
            }

        }
    }
    }
}