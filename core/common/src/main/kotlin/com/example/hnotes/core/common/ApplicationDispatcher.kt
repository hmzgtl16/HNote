package com.example.hnotes.core.common

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationDispatcher(val dispatcher: Dispatcher)

enum class Dispatcher {
    DEFAULT,
    IO
}