package com.example.hnote.core.database.di

import com.example.hnote.core.database.ApplicationDatabase
import com.example.hnote.core.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideNoteDao(database: ApplicationDatabase): NoteDao {
        return database.noteDao()
    }
}