package com.cottondroid.todoachieved.task;

import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;
import com.cottondroid.todoachieved.task.network.TaskUpdateListener;
import com.cottondroid.todoachieved.task.network.TodoTaskService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.cottondroid.todoachieved.di.ApplicationModule.IO_SCHEDULER;
import static com.cottondroid.todoachieved.di.ApplicationModule.UI_SCHEDULER;

@Singleton
public class TaskRepository {

    private final TodoTaskDao taskDao;
    private final TodoTaskService todoTaskService;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    @Inject
    public TaskRepository(TodoTaskDao taskDao,
                          TodoTaskService todoTaskService,
                          @Named(UI_SCHEDULER) Scheduler uiScheduler,
                          @Named(IO_SCHEDULER) Scheduler ioScheduler) {
        this.taskDao = taskDao;
        this.todoTaskService = todoTaskService;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    public Flowable<List<TodoTask>> getTasks() {
        return taskDao.list()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public void registerToTaskUpdates(final TaskUpdateListener taskListener) {
        taskDao.getLastServerId()
                .defaultIfEmpty("")
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler).subscribe(new Consumer<String>() {
            @Override
            public void accept(String lastServerId) throws Exception {
                todoTaskService.registerToTaskUpdates(taskListener, lastServerId);
            }
        });
    }

    public void unregisterFromTaskUpdates(TaskUpdateListener taskListener) {
        todoTaskService.unregisterFromTaskUpdates(taskListener);
    }

    public Single<TodoTask> saveTask(final TodoTask todoTask) {
        return taskDao.insertOrReplace(todoTask)
                .flatMap(new Function<Long, SingleSource<TodoTask>>() {
                    @Override
                    public SingleSource<TodoTask> apply(Long taskId) throws Exception {
                        return taskDao.load(taskId);
                    }
                })
                .doOnSuccess(new Consumer<TodoTask>() {
                    @Override
                    public void accept(TodoTask task) throws Exception {
                        todoTaskService.populateServerValues(task);
                        taskDao.updateSync(task);
                        todoTaskService.saveTask(task);
                    }
                })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public Single<TodoTask> loadTask(final long taskId) {
        return taskDao.load(taskId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}
