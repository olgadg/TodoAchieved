package com.example.olgadominguez.todoachieved.task.list;

import com.example.olgadominguez.todoachieved.task.TaskRepository;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.example.olgadominguez.todoachieved.di.ApplicationModule.IO_SCHEDULER;
import static com.example.olgadominguez.todoachieved.di.ApplicationModule.UI_SCHEDULER;

public class TaskListPresenter {
    private final TaskRepository taskRepository;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    @Inject
    public TaskListPresenter(TaskRepository taskRepository,
                             @Named(UI_SCHEDULER) Scheduler uiScheduler,
                             @Named(IO_SCHEDULER) Scheduler ioScheduler) {
        this.taskRepository = taskRepository;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    public Single<List<TodoTask>> getItems() {
        return taskRepository.getTasks()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}
