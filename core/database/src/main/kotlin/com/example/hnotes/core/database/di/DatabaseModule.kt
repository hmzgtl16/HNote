package com.example.hnotes.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.hnotes.core.database.ApplicationDatabase
import com.example.hnotes.core.database.util.ItemCallback
import com.example.hnotes.core.database.util.NoteCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ApplicationDatabase = Room.databaseBuilder(
        context = context,
        klass = ApplicationDatabase::class.java,
        name = "hnote_database"
    )
        .addCallback(callback = NoteCallback)
        .addCallback(callback = ItemCallback)
        .build()

}