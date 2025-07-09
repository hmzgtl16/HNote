package com.example.hnote.core.navigation.di

import com.example.hnote.core.navigation.Navigator
import com.example.hnote.core.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NavigatorModule {

    @Provides
    @Singleton
    internal fun bindsNavigator(): Navigator = NavigatorImpl()
}