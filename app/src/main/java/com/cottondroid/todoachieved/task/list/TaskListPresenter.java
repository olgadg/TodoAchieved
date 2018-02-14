package com.cottondroid.todoachieved.task.list;

import com.cottondroid.todoachieved.task.TaskRepository;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.network.TaskUpdateListener;

import javax.inject.Inject;
import java.util.List;

import io.reactivex.Flowable;

public class TaskListPresenter {
    private final TaskRepository taskRepository;
    private final TaskUpdateListener taskListener;

    @Inject
    public TaskListPresenter(TaskRepository taskRepository,
                             TaskUpdateListener taskListener) {
        this.taskRepository = taskRepository;
        this.taskListener = taskListener;
    }

    public Flowable<List<TodoTask>> getItems() {
        return taskRepository.getTasks();
    }

    public void onResume() {
        taskRepository.registerToTaskUpdates(taskListener);
    }

    public void onLogin() {
        taskRepository.registerToTaskUpdates(taskListener);
    }

    public void onPause() {
        taskRepository.unregisterFromTaskUpdates(taskListener);
    }
}
