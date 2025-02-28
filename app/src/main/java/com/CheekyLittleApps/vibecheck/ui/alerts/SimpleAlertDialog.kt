package com.CheekyLittleApps.vibecheck.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleAlertDialog(
    viewModel: MainViewModel,
    onDismiss: () -> Unit
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
                Text(text = "This will delete all of your data and there is no way to retrieve it. Are you sure you want to do this?")
                Spacer(modifier = Modifier.padding(24.dp))
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDismiss()
                        viewModel.nuke()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Confirm")
                }
            }
        }


    }
}