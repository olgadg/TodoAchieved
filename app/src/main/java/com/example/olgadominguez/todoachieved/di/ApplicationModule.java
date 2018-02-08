package com.example.olgadominguez.todoachieved.di;

import android.content.Context;

import com.example.olgadominguez.todoachieved.TodoApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Context provideContext(TodoApplication application) {
        return application;
    }
}
