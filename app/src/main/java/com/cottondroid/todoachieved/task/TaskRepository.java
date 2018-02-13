package com.cottondroid.todoachieved.task;

import com.cottondroid.todoachieved.auth.AuthenticationRepository;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;
import com.cottondroid.todoachieved.task.network.TaskUpdateListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

import static com.cottondroid.todoachieved.network.NetworkModule.TASKS_DB;

@Singleton
public class TaskRepository {

    private final TodoTaskDao taskDao;
    private final Provider<DatabaseReference> dbRefeferenceProvider;
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public TaskRepository(TodoTaskDao taskDao,
                          @Named(TASKS_DB) Provider<DatabaseReference> dbRefeferenceProvider,
                          AuthenticationRepository authenticationRepository) {
        this.taskDao = taskDao;
        this.dbRefeferenceProvider = dbRefeferenceProvider;
        this.authenticationRepository = authenticationRepository;
    }

    public Flowable<List<TodoTask>> getTasks() {
        return taskDao.list();
    }

    public void registerToTaskUpdates(TaskUpdateListener taskListener) {
        if (authenticationRepository.isLoggedIn()) {
            DatabaseReference dbReference = dbRefeferenceProvider.get();
            dbReference.addChildEventListener(taskListener);
        }
    }

    public void unregisterFromTaskUpdates(TaskUpdateListener taskListener) {
        dbRefeferenceProvider.get().removeEventListener((ChildEventListener) taskListener);
    }

    public Single<TodoTask> saveTask(final TodoTask todoTask) {
        return taskDao.insertOrReplace(todoTask)
                .flatMap(new Function<Long, SingleSource<? extends TodoTask>>() {
                    @Override
                    public SingleSource<? extends TodoTask> apply(Long id) throws Exception {
                        return taskDao.load(id);
                    }
                })
                .map(new Function<TodoTask, TodoTask>() {
                    @Override
                    public TodoTask apply(TodoTask todoTask) throws Exception {
                        if (authenticationRepository.isLoggedIn()) {
                            String serverId = todoTask.getServerId();
                            if (todoTask.getServerId() == null) {
                                serverId = dbRefeferenceProvider.get().push().getKey();
                                todoTask.setServerId(serverId);
                                taskDao.updateSync(todoTask);
                                todoTask.setServerCreatedTimestamp(ServerValue.TIMESTAMP);
                            }
                            todoTask.setServerUpdatedTimestamp(ServerValue.TIMESTAMP);
                            dbRefeferenceProvider.get().child(serverId).setValue(todoTask);
                        }
                        return todoTask;
                    }
                });
    }

    public Single<TodoTask> loadTask(final long taskId) {
        return taskDao.load(taskId);
    }
}
