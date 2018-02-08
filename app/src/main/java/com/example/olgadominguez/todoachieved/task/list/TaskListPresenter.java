package com.example.olgadominguez.todoachieved.task.list;

import com.example.olgadominguez.todoachieved.task.TaskRepository;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import javax.inject.Inject;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskListPresenter {
    private final TaskRepository taskRepository;

    private TaskListView view;

    @Inject
    public TaskListPresenter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void setView(TaskListView view) {
        this.view = view;
    }

    public void getItems() {

        Subscriber<List<TodoTask>> subscriber = new Subscriber<List<TodoTask>>() {
            @Override
            public void onCompleted() {
                view.onFinishLoading();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<TodoTask> todoTasks) {
                view.showTasks(todoTasks);
            }
        };
        taskRepository.getTasks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addTodoTask() {
        view.onAddTodoTask();
    }
}
