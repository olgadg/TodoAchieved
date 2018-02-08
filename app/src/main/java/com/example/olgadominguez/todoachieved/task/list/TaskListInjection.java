package com.example.olgadominguez.todoachieved.task.list;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TaskListInjection {

    @ContributesAndroidInjector
    abstract TaskListFragment taskListFragment();

}
