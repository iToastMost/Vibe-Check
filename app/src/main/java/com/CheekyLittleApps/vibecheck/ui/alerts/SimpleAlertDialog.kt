package com.CheekyLittleApps.vibecheck.ui.alerts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleAlertDialog(
    viewModel: MainViewModel,
    onDismiss: () -> Unit,
    text: String,
    onClickConfirm: () -> Unit,
    )
{
    val openDialog = remember { mutableStateOf(true) }

    BasicAlertDialog(
        onDismissRequest = {
            openDialog.value = false
            onDismiss()
        },
    ){
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = text)
                Spacer(modifier = Modifier.padding(24.dp))
                Box(Modifier.fillMaxWidth()){
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onDismiss()
                        },
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(text = "Cancel")
                    }

                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onDismiss()
                            onClickConfirm()
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