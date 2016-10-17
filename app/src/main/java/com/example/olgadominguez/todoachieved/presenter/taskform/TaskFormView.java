package com.example.olgadominguez.todoachieved.presenter.taskform;

import com.example.olgadominguez.todoachieved.model.TodoTask;

import java.util.Calendar;

public interface TaskFormView {

    void onItemAdded(TodoTask todoTask);

    void onDateTimeChanged(Calendar taskDate);
}
