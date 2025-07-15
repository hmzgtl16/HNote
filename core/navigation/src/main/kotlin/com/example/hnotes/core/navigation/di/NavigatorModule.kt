package com.example.hnotes.core.navigation.di

import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.core.navigation.NavigatorImpl
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