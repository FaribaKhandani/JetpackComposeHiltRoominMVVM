package com.example.jetpackcomposehiltroominmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.jetpackcomposehiltroominmvvm.NoteApp
import com.example.jetpackcomposehiltroominmvvm.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                NoteApp()
            }
        }
    }
}