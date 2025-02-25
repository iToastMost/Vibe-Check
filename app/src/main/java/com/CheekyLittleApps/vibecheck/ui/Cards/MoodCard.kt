package com.CheekyLittleApps.vibecheck.ui.Cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun MoodCard(
    mood: String,
    moodText: String,
    date: String,
    uId: Int,
    //onClickViewMood: (String) -> Unit = {},
    onClickViewMood: (Int) -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        onClick = { onClickViewMood(uId) },
        modifier = Modifier.size(width = 280.dp, height = 120.dp).padding(4.dp)
    ) {
        Column {
            Text(text = date, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
            Text(text = mood, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)

            //for checking databse id on card
            //Text(text = uId.toString(),  modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) { Text(text = moodText, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center) }
        }
    }
}