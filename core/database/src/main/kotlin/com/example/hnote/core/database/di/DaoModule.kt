package com.example.hnote.core.database.di

import com.example.hnote.core.database.ApplicationDatabase
import com.example.hnote.core.database.dao.NoteDao
import com.example.hnote.core.database.dao.SearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideNoteDao(database: ApplicationDatabase): NoteDao =
        database.noteDao()

    @Provides
    fun provideSearchQueryDao(database: ApplicationDatabase): SearchQueryDao =
        database.searchQueryDao()
}