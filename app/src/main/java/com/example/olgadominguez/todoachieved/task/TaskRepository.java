package com.example.olgadominguez.todoachieved.task;

import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.model.TodoTaskDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

@Singleton
public class TaskRepository {
    private final TodoTaskDao taskDao;

    @Inject
    public TaskRepository(TodoTaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Single<List<TodoTask>> getTasks() {
        return Single.fromCallable(new Callable<List<TodoTask>>() {
            @Override
            public List<TodoTask> call() throws Exception {

                return taskDao.list();
            }
        });
    }

    public Single<TodoTask> saveTask(final TodoTask todoTask) {
        return Single.fromCallable(new Callable<TodoTask>() {
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

    public Single<TodoTask> loadTask(final long taskId) {
        return taskDao.load(taskId);
    }
}
