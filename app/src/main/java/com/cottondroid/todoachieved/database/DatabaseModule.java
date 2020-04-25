package com.cottondroid.todoachieved.database;

import androidx.room.Room;

import com.cottondroid.todoachieved.TodoApplication;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private static final String DATABASE_NAME = "todoachieved-db";

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(TodoApplication application) {
        return Room.databaseBuilder(
                application,
                AppDatabase.class, DATABASE_NAME
        ).build();
    }

    @Provides
    @Singleton
    TodoTaskDao provideTodoTaskDao(AppDatabase appDatabase) {
        return appDatabase.todoTaskDao();
    }
}
