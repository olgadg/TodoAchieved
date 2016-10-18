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
    private TodoTask todoTask;
    private Calendar taskDate;

    public TaskFormPresenter(TaskFormView view, DaoSession daoSession) {
        taskRepository = new TaskRepository(daoSession);
        this.view = view;
    }

    public void saveTodoTask(String taskName) {
        if (todoTask == null) {
            todoTask = new TodoTask();
            todoTask.setCreatedDate(Calendar.getInstance().getTimeInMillis());
        }
        todoTask.setText(taskName);
        if (taskDate != null) {
            todoTask.setDate(taskDate.getTimeInMillis());
        }
        saveTodoTask(todoTask);
    }

    public void saveTodoTask(final TodoTask todoTask) {


        Subscriber<TodoTask> subscriber = new Subscriber<TodoTask>() {
            @Override
            public void onCompleted() {
                view.onItemAdded(todoTask);
            }

            @Override
            public void onError(Throwable e) {
                view.onErrorSavingTask(e);

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

    public void loadTodoTask(final long taskId) {


        Subscriber<TodoTask> subscriber = new Subscriber<TodoTask>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onErrorLoadingTask(e);
            }

            @Override
            public void onNext(TodoTask loadedTodoTask) {
                setTodoTask(loadedTodoTask);
            }
        };
        taskRepository.loadTask(taskId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void setTodoTask(TodoTask loadedTodoTask) {
        todoTask = loadedTodoTask;
        if (todoTask.getDate() != null) {
            taskDate = Calendar.getInstance();
            taskDate.setTimeInMillis(todoTask.getDate());
        }
        view.onTaskLoaded(loadedTodoTask);
    }

    public void chooseDate(int year, int month, int dayOfTheMonth) {
        if (taskDate == null) {
            taskDate = Calendar.getInstance();
        }
        taskDate.set(year, month, dayOfTheMonth);
        view.onDateTimeChanged(taskDate);
    }

    public void chooseTime(int hour, int minute) {
        taskDate.set(Calendar.HOUR, hour);
        taskDate.set(Calendar.MINUTE, minute);
        view.onDateTimeChanged(taskDate);
    }

    public Calendar getTaskDate() {
        if (taskDate != null) {
            return taskDate;
        }
        return Calendar.getInstance();
    }
}
