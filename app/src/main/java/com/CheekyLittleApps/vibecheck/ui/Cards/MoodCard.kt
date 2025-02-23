package com.CheekyLittleApps.vibecheck.ui.Cards

import androidx.compose.foundation.layout.Box
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
        //onClick = onClickViewMood,
        modifier = Modifier.size(width = 280.dp, height = 100.dp).padding(4.dp)
    ) {
        Text(text = date, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
        Text(text = mood, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
        Text(text = uId.toString(),  modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
        Box(Modifier.fillMaxSize()) { Text(text = moodText, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center) }
    }
}