package com.example.olgadominguez.todoachieved.task;

import com.example.olgadominguez.todoachieved.task.model.DaoSession;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.model.TodoTaskDao;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

public class TaskRepository {
    private final TodoTaskDao taskDao;

    public TaskRepository(DaoSession daoSession) {
        taskDao = daoSession.getTodoTaskDao();
    }

    public Observable<List<TodoTask>> getTasks() {
        return Observable.fromCallable(new Callable<List<TodoTask>>() {
            @Override
            public List<TodoTask> call() throws Exception {

                return taskDao.queryBuilder()
                        .orderAsc(TodoTaskDao.Properties.CreatedDate)
                        .build().list();
            }
        });
    }

    public Observable<TodoTask> saveTask(final TodoTask todoTask) {
        return Observable.fromCallable(new Callable<TodoTask>() {
            @Override
            public TodoTask call() throws Exception {
                if (todoTask.getId() != null) {
                    taskDao.updateInTx(todoTask);
                } else {
                    taskDao.insertInTx(todoTask);
                }
                return todoTask;
            }
        });
    }

    public Observable<TodoTask> loadTask(final long taskId) {
        return Observable.fromCallable(new Callable<TodoTask>() {
            @Override
            public TodoTask call() throws Exception {

                TodoTask todoTask = taskDao.load(taskId);
                return todoTask;
            }
        });
    }
}
