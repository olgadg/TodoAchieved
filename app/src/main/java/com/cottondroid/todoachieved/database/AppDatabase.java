package com.cottondroid.todoachieved.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;

@Database(entities = {TodoTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoTaskDao todoTaskDao();
}
