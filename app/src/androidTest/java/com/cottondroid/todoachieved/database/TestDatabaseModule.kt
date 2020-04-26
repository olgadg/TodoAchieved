package com.cottondroid.todoachieved.database

import androidx.room.Room
import com.cottondroid.todoachieved.TodoApplication
import com.cottondroid.todoachieved.task.model.TodoTaskDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: TodoApplication): AppDatabase {
        return Room.inMemoryDatabaseBuilder(application, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun provideTodoTaskDao(appDatabase: AppDatabase): TodoTaskDao {
        return appDatabase.todoTaskDao()
    }
}