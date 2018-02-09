package com.cottondroid.todoachieved.task.form;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TaskFormInjection {
    @ContributesAndroidInjector
    abstract TaskFormFragment bindTaskFormFragment();
}
