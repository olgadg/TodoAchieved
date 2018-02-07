package com.example.olgadominguez.todoachieved.task.list;

import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import java.util.List;

public interface TaskListView {

    void showTasks(List<TodoTask> todoTasks);

    void onFinishLoading();

    void onAddTodoTask();
}
