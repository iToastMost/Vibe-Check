package com.CheekyLittleApps.vibecheck.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val date: String,
    @ColumnInfo val time: Long,
    @ColumnInfo val mood: String
)
