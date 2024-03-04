package com.example.jetpackcomposehiltroominmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposehiltroominmvvm.room.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel  @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<NoteModel>> = noteRepository.allNotes


    fun insert(note: NoteModel) = viewModelScope.launch {
        noteRepository.insert(note)
        navigateBackToList()
    }

    fun delete(note: NoteModel) = viewModelScope.launch {
        noteRepository.delete(note)
    }

    fun updateText(noteId: Long, newText: String) {
        viewModelScope.launch(Dispatchers.IO){
            val existingNote = noteRepository.getNoteById(noteId)
            existingNote.collect { note ->
                note?.let {
                    val updatedNote = it.copy(text = newText)
                    noteRepository.update(updatedNote)
                }
            }

        }

    }



    private val _currentScreen = MutableStateFlow<NoteScreen>(NoteScreen.NotesList)
    val currentScreen: StateFlow<NoteScreen> = _currentScreen.asStateFlow()

    fun navigateToDetail(noteId:Long) {
        _currentScreen.value = NoteScreen.NoteDetail(noteId)
    }

    fun navigateBackToList() {
        _currentScreen.value = NoteScreen.NotesList
    }

    fun navigateToNoteEntry() {
        _currentScreen.value = NoteScreen.NoteEntry
    }



    fun getNoteById(noteId: Long): Flow<NoteModel?> {
        return noteRepository.getNoteById(noteId)
    }






}


