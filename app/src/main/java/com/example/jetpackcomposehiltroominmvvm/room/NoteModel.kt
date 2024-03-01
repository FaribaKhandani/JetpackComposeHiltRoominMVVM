package com.example.jetpackcomposehiltroominmvvm.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var text: String,
    val timestamp: Long = System.currentTimeMillis()

)