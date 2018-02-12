package com.cottondroid.todoachieved.task.list;

import com.cottondroid.todoachieved.task.TaskRepository;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.network.TaskUpdateListener;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

import static com.cottondroid.todoachieved.di.ApplicationModule.IO_SCHEDULER;
import static com.cottondroid.todoachieved.di.ApplicationModule.UI_SCHEDULER;

public class TaskListPresenter {
    private final TaskRepository taskRepository;
    private final TaskUpdateListener taskListener;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    @Inject
    public TaskListPresenter(TaskRepository taskRepository,
                             TaskUpdateListener taskListener,
                             @Named(UI_SCHEDULER) Scheduler uiScheduler,
                             @Named(IO_SCHEDULER) Scheduler ioScheduler) {
        this.taskRepository = taskRepository;
        this.taskListener = taskListener;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    public Flowable<List<TodoTask>> getItems() {
        taskRepository.registerToTaskUpdates(taskListener);
        return taskRepository.getTasks()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public void onLogin() {
        taskRepository.registerToTaskUpdates(taskListener);
    }

    public void onDetach() {
        taskRepository.unregisterFromTaskUpdates(taskListener);
    }
}
