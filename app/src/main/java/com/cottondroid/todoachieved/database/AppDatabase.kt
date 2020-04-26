package com.cottondroid.todoachieved.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cottondroid.todoachieved.task.model.TodoTask
import com.cottondroid.todoachieved.task.model.TodoTaskDao

@Database(entities = [TodoTask::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoTaskDao(): TodoTaskDao
}