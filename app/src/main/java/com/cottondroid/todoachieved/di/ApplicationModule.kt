package com.cottondroid.todoachieved.di

import android.content.Context
import com.cottondroid.todoachieved.TodoApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun provideContext(application: TodoApplication): Context {
        return application
    }
}