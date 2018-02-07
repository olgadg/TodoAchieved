package com.example.olgadominguez.todoachieved.task.form;

import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import java.util.Calendar;

public interface TaskFormView {

    void onItemAdded(TodoTask todoTask);

    void onDateTimeChanged(Calendar taskDate);

    void onTaskLoaded(TodoTask todoTask);

    void onErrorSavingTask(Throwable e);

    void onErrorLoadingTask(Throwable e);
}
