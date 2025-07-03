package com.example.hnote.core.common.di

import com.example.hnote.core.common.ApplicationDispatcher
import com.example.hnote.core.common.ApplicationScope
import com.example.hnote.core.common.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApplicationScopeModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun providesCoroutineScope(
        @ApplicationDispatcher(dispatcher = Dispatcher.DEFAULT) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}