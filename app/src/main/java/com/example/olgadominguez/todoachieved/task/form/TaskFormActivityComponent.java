package com.example.olgadominguez.todoachieved.task.form;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface TaskFormActivityComponent extends AndroidInjector<TaskFormActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TaskFormActivity> {
    }
}
