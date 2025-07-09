package com.example.hnote.core.navigation.di

import com.example.hnote.core.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    internal abstract fun bindsNavigator(
        navigator: NavigatorImpl
    ): NavigatorImpl
}