package com.cottondroid.todoachieved.database

import androidx.room.Room
import com.cottondroid.todoachieved.TodoApplication
import com.cottondroid.todoachieved.task.model.TodoTaskDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: TodoApplication): AppDatabase {
        return Room.databaseBuilder(
                application,
                AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoTaskDao(appDatabase: AppDatabase): TodoTaskDao {
        return appDatabase.todoTaskDao()
    }

    companion object {
        private const val DATABASE_NAME = "todoachieved-db"
    }
}