package com.example.olgadominguez.todoachieved.task;

import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.model.TodoTaskDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

@Singleton
public class TaskRepository {
    private final TodoTaskDao taskDao;

    @Inject
    public TaskRepository(TodoTaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Observable<List<TodoTask>> getTasks() {
        return Observable.fromCallable(new Callable<List<TodoTask>>() {
            @Override
            public List<TodoTask> call() throws Exception {

                return taskDao.list();
            }
        });
    }

    public Observable<TodoTask> saveTask(final TodoTask todoTask) {
        return Observable.fromCallable(new Callable<TodoTask>() {
            @Override
            public TodoTask call() throws Exception {
                if (todoTask.getId() != null) {
                    taskDao.update(todoTask);
                } else {
                    taskDao.insert(todoTask);
                }
                return todoTask;
            }
        });
    }

    public Observable<TodoTask> loadTask(final long taskId) {
        return Observable.fromCallable(new Callable<TodoTask>() {
            @Override
            public TodoTask call() throws Exception {
                return taskDao.load(taskId);
            }
        });
    }
}
