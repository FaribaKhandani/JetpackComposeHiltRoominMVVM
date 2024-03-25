package com.example.jetpackcomposehiltroominmvvm.ui


import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity


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