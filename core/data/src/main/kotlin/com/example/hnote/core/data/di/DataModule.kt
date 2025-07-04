package com.example.hnote.core.data.di

import com.example.hnote.core.data.repository.NoteRepository
import com.example.hnote.core.data.repository.NoteRepositoryImpl
import com.example.hnote.core.data.repository.UserDataRepository
import com.example.hnote.core.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    internal abstract fun bindsNoteRepository(
        noteRepository: NoteRepositoryImpl
    ): NoteRepository
}