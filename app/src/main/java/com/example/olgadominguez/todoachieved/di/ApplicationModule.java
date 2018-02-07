package com.example.olgadominguez.todoachieved.di;

import android.content.Context;

import com.example.olgadominguez.todoachieved.TodoApplication;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivityComponent;
import com.example.olgadominguez.todoachieved.task.list.TaskListActivityComponent;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {
        TaskFormActivityComponent.class,
        TaskListActivityComponent.class})
public class ApplicationModule {

    @Provides
    @Application
    Context provideContext(TodoApplication application) {
        return application;
    }
}
