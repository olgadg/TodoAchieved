package com.cottondroid.todoachieved.network

import com.google.firebase.database.DatabaseReference
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestNetworkModule {
    @Singleton
    @Provides
    fun databaseReference(): DatabaseReference = mock()

    @Provides
    @Named(TASKS_DB)
    fun getTaskDatabaseReference(): DatabaseReference = mock()

    companion object {
        const val TASKS_DB = "tasks"
    }
}