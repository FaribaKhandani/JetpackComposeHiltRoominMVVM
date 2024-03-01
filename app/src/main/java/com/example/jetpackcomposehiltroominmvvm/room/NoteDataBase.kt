package com.example.jetpackcomposehiltroominmvvm.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 2)
abstract class NoteDataBase  : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}