package com.example.hnote.core.alarm.di

import com.example.hnote.core.alarm.AlarmScheduler
import com.example.hnote.core.alarm.AlarmSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AlarmModule {

    @Binds
    abstract fun bindAlarmScheduler(
        notifier: AlarmSchedulerImpl,
    ): AlarmScheduler
}