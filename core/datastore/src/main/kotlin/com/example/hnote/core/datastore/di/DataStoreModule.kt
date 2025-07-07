package com.example.hnote.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.hnote.core.common.ApplicationDispatcher
import com.example.hnote.core.common.ApplicationScope
import com.example.hnote.core.common.Dispatcher
import com.example.hnote.core.datastore.UserPreferences
import com.example.hnote.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @ApplicationDispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory
        .create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + dispatcher),
            produceFile = { context.dataStoreFile(fileName = "user_preferences.pb") }
        )
}