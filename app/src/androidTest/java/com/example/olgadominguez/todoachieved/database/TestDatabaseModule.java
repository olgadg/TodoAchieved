package com.example.olgadominguez.todoachieved.database;

import android.arch.persistence.room.Room;

import com.example.olgadominguez.todoachieved.TodoApplication;
import com.example.olgadominguez.todoachieved.task.model.TodoTaskDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestDatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(TodoApplication application) {
        return Room.inMemoryDatabaseBuilder(application, AppDatabase.class).build();
    }

    @Provides
    @Singleton
    TodoTaskDao provideTodoTaskDao(AppDatabase appDatabase) {
        return appDatabase.todoTaskDao();
    }
}
