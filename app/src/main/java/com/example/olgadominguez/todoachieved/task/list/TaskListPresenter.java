package com.example.olgadominguez.todoachieved.task.list;

import com.example.olgadominguez.todoachieved.task.model.DaoSession;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.TaskRepository;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskListPresenter {
    private final TaskRepository taskRepository;

    private TaskListView view;

    public TaskListPresenter(TaskListView view, DaoSession daoSession) {
        taskRepository = new TaskRepository(daoSession);
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
