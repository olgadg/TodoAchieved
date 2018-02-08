package com.example.olgadominguez.todoachieved.di;

import android.content.Context;

import com.example.olgadominguez.todoachieved.TodoApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {
    public static final String UI_SCHEDULER = "UiScheduler";
    public static final String IO_SCHEDULER = "IoScheduler";

    @Provides
    @Singleton
    Context provideContext(TodoApplication application) {
        return application;
    }

    @Provides
    @Named(UI_SCHEDULER)
    public Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named(IO_SCHEDULER)
    public Scheduler provideIoScheduler() {
        return Schedulers.io();
    }
}
