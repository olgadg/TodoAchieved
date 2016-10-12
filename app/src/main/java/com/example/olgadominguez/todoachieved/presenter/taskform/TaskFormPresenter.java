package com.example.olgadominguez.todoachieved.presenter.taskform;


import com.example.olgadominguez.todoachieved.model.DaoSession;
import com.example.olgadominguez.todoachieved.model.TodoTask;
import com.example.olgadominguez.todoachieved.repository.TaskRepository;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskFormPresenter {

    private final TaskRepository taskRepository;

    private TaskFormView view;

    public TaskFormPresenter(TaskFormView view, DaoSession daoSession) {
        taskRepository = new TaskRepository(daoSession);
        this.view = view;
    }

    public void saveTodoTask(final TodoTask todoTask) {


        Subscriber<TodoTask> subscriber = new Subscriber<TodoTask>() {
            @Override
            public void onCompleted() {
                view.onItemAdded(todoTask);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TodoTask todoTask) {

            }
        };
        taskRepository.saveTask(todoTask)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
