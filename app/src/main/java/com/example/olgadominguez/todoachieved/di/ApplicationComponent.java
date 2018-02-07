package com.example.olgadominguez.todoachieved.di;

import android.app.Activity;

import com.example.olgadominguez.todoachieved.task.form.TaskFormActivity;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivityComponent;
import com.example.olgadominguez.todoachieved.task.list.TaskListActivity;
import com.example.olgadominguez.todoachieved.task.list.TaskListActivityComponent;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ApplicationComponent.ActivityBuilder.class})
public interface ApplicationComponent {

    @Module
    abstract class ActivityBuilder {

        @Binds
        @IntoMap
        @ActivityKey(TaskFormActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bindTaskFormActivity(TaskFormActivityComponent.Builder builder);

        @Binds
        @IntoMap
        @ActivityKey(TaskListActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bindTaskListActivity(TaskListActivityComponent.Builder builder);

    }
}
