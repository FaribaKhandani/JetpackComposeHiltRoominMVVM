package com.example.jetpackcomposehiltroominmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackcomposehiltroominmvvm.room.NoteDao
import com.example.jetpackcomposehiltroominmvvm.room.NoteDataBase
import com.example.jetpackcomposehiltroominmvvm.viewmodel.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {


    @Provides
    @Singleton
    fun provideDatabaseNote(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(context, NoteDataBase::class.java,"note_db").fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideDaoNote(databaseNote: NoteDataBase): NoteDao {
        return databaseNote.noteDao()
    }


    @Provides
    @Singleton
    fun provideRepositoryNote(daoNote: NoteDao): NoteRepository {
        return NoteRepository(daoNote)
    }


}