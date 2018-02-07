package com.example.olgadominguez.todoachieved.task.list;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface TaskListActivityComponent extends AndroidInjector<TaskListActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TaskListActivity> {
    }
}
