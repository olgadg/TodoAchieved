package com.cottondroid.todoachieved.task.list;

import com.cottondroid.todoachieved.task.TaskRepository;
import com.cottondroid.todoachieved.task.model.TodoTask;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

import static com.cottondroid.todoachieved.di.ApplicationModule.IO_SCHEDULER;
import static com.cottondroid.todoachieved.di.ApplicationModule.UI_SCHEDULER;

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

    public Flowable<List<TodoTask>> getItems() {
        return taskRepository.getTasks()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}
