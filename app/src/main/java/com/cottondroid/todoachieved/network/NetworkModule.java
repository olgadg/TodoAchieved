package com.cottondroid.todoachieved.network;

import com.cottondroid.todoachieved.auth.AuthenticationRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    public static final String TASKS_DB = "tasks";

    @Provides
    @Singleton
    public DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    @Named(TASKS_DB)
    public DatabaseReference getTaskDatabaseReference(
            DatabaseReference databaseReference,
            AuthenticationRepository authenticationRepository) {
        return databaseReference
                .child(authenticationRepository.getUserId())
                .child(TASKS_DB);
    }
}
