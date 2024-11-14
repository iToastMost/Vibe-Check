package com.CheekyLittleApps.vibecheck.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.CheekyLittleApps.vibecheck.data.MoodDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry

@Composable
fun MyApp(db: MoodDatabase) {

    // MutableState to keep track of the input text
    var text by remember { mutableStateOf("") }
    // List to store user input
    var itemList by remember { mutableStateOf(listOf<String>()) }

    // Column Layout for Text Input and List Display
    Column(modifier = Modifier.padding(50.dp)) {
        // Text Field for user input
        BasicTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Button to submit input and add to the list
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    // Add input text to the list and clear input field
                    itemList = itemList + text
                    text = ""
//                    MoodEntry entry = new MoodEntry(0, "", "", text)
//                    db.moodDao().insertAll(moodEntry)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }

        // Display list of items
        itemList.forEach { entry ->
            Text(text = entry, modifier = Modifier.padding(top = 8.dp))
        }
    }
}