package com.example.hnotes.core.notification.di

import com.example.hnotes.core.notification.Notifier
import com.example.hnotes.core.notification.NotifierImpl
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