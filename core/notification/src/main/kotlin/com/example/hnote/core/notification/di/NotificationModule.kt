package com.example.hnote.core.notification.di

import com.example.hnote.core.notification.Notifier
import com.example.hnote.core.notification.NotifierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationModule {
    @Binds
    abstract fun bindNotifier(
        notifier: NotifierImpl,
    ): Notifier
}