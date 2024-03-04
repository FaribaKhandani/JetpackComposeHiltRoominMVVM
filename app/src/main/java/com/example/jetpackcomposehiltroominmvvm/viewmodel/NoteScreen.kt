package com.example.jetpackcomposehiltroominmvvm.viewmodel

sealed class NoteScreen {
    object NotesList : NoteScreen()
    data class NoteDetail(val noteId: Long) : NoteScreen()
    object NoteEntry : NoteScreen()
}