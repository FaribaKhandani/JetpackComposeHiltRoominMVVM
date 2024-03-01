package com.example.jetpackcomposehiltroominmvvm.viewmodel

import androidx.lifecycle.LiveData
import com.example.jetpackcomposehiltroominmvvm.room.NoteDao
import com.example.jetpackcomposehiltroominmvvm.room.NoteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<NoteModel>> = noteDao.getAllNotes()

    suspend fun insert(note: NoteModel) {
        noteDao.insert(note)
    }

    suspend fun delete(note: NoteModel) {
        noteDao.delete(note)
    }

    fun getNoteById(noteId: Long): Flow<NoteModel?> {
        return noteDao.getNoteById(noteId)
    }

    fun update(note: NoteModel){
        noteDao.update(note)
    }
}