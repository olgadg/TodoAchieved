package com.example.olgadominguez.todoachieved.presenter.tasklist;

import com.example.olgadominguez.todoachieved.model.TodoTask;

import java.util.List;

public interface TaskListView {

    void showTasks(List<TodoTask> todoTasks);

    void onFinishLoading();

    void onAddTodoTask();
}
