package com.example.jetpackcomposehiltroominmvvm.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteModel>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)


    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): Flow<NoteModel?>

    @Update
    fun update(note: NoteModel)
}