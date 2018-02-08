package com.example.olgadominguez.todoachieved.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.model.TodoTaskDao;

@Database(entities = {TodoTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoTaskDao todoTaskDao();
}
