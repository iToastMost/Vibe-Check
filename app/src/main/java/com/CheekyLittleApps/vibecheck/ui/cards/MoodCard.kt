package com.CheekyLittleApps.vibecheck.ui.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
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
    val bottomFade = Brush.verticalGradient(0.6f to Color.Black, 0.99f to Color.Transparent)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            onClick = { onClickViewMood(uId) },
            modifier = Modifier.size(width = 280.dp, height = 160.dp).padding(4.dp)
        ) {
            Column(modifier = Modifier.fadingEdge(bottomFade)) {
                //Text(text = date, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
                Text(text = mood, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)

                //for checking databse id on card
                //Text(text = uId.toString(),  modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) { Text(text = moodText, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center) }
            }
        }
        Text(text = date, textAlign = TextAlign.Center)
    }




}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent{
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }