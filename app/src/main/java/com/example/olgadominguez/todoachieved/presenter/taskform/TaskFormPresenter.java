package com.example.olgadominguez.todoachieved.presenter.taskform;


import com.example.olgadominguez.todoachieved.model.DaoSession;
import com.example.olgadominguez.todoachieved.model.TodoTask;
import com.example.olgadominguez.todoachieved.repository.TaskRepository;

import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskFormPresenter {

    private final TaskRepository taskRepository;

    private TaskFormView view;
    private Calendar taskDate = Calendar.getInstance();

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

    public void chooseDate(int year, int month, int dayOfTheMonth) {
        taskDate.set(year, month, dayOfTheMonth);
        view.onDateTimeChanged(taskDate);
    }

    public void chooseTime(int hour, int minute) {
        taskDate.set(Calendar.HOUR, hour);
        taskDate.set(Calendar.MINUTE, minute);
        view.onDateTimeChanged(taskDate);
    }

    public void onViewSet() {
        view.onDateTimeChanged(taskDate);
    }

    public Calendar getTaskDate() {
        return taskDate;
    }
}
