package com.CheekyLittleApps.vibecheck.ui.viewmood

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel

@Composable
fun ViewMoodScreen(
    viewModel: MainViewModel,
    //uId: String,
    //onClickViewMood: (String) -> Unit = {}
    onClickViewMood: () -> Unit = {}
) {
    Text(
        text = "uId",
        modifier = Modifier.padding(16.dp)
    )
}