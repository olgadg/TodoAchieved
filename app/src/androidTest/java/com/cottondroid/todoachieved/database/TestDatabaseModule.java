package com.cottondroid.todoachieved.database;

import android.arch.persistence.room.Room;

import com.cottondroid.todoachieved.TodoApplication;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;

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
