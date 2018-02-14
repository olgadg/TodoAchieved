package com.cottondroid.todoachieved.task.network;

import com.cottondroid.todoachieved.auth.AuthenticationRepository;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import static com.cottondroid.todoachieved.network.NetworkModule.TASKS_DB;

@Singleton
public class TodoTaskService {
    private final Provider<DatabaseReference> dbRefeferenceProvider;
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public TodoTaskService(@Named(TASKS_DB) Provider<DatabaseReference> dbRefeferenceProvider,
                           AuthenticationRepository authenticationRepository) {
        this.dbRefeferenceProvider = dbRefeferenceProvider;
        this.authenticationRepository = authenticationRepository;
    }

    public void registerToTaskUpdates(TaskUpdateListener taskListener, String lastServerId) {
        if (authenticationRepository.isLoggedIn()) {
            DatabaseReference dbReference = dbRefeferenceProvider.get();
            Query query = dbReference.orderByKey();
            if (lastServerId != null && !lastServerId.isEmpty()) {
                query = query.startAt(lastServerId);
            }
            query.addChildEventListener(taskListener);
        }
    }

    public void unregisterFromTaskUpdates(TaskUpdateListener taskListener) {
        if (authenticationRepository.isLoggedIn()) {
            dbRefeferenceProvider.get().removeEventListener(taskListener);
        }
    }

    public void saveTask(TodoTask todoTask) {
        if (authenticationRepository.isLoggedIn()) {
            dbRefeferenceProvider.get().child(todoTask.getServerId()).setValue(todoTask);
        }
    }

    public void populateServerValues(TodoTask todoTask) {
        if (authenticationRepository.isLoggedIn()) {
            String serverId = todoTask.getServerId();
            if (serverId == null) {
                serverId = dbRefeferenceProvider.get().push().getKey();
                todoTask.setServerId(serverId);
                todoTask.setServerCreatedTimestamp(ServerValue.TIMESTAMP);
            }
            todoTask.setServerUpdatedTimestamp(ServerValue.TIMESTAMP);
        }
    }
}
