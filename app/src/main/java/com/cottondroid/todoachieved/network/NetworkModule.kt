package com.cottondroid.todoachieved.network

import com.cottondroid.todoachieved.auth.AuthenticationRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun databaseReference(): DatabaseReference {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setPersistenceEnabled(true)
        return firebaseDatabase.reference
    }

    @Provides
    @Named(TASKS_DB)
    fun getTaskDatabaseReference(
            databaseReference: DatabaseReference,
            authenticationRepository: AuthenticationRepository): DatabaseReference {
        return databaseReference
                .child(authenticationRepository.userId!!)
                .child(TASKS_DB)
    }

    companion object {
        const val TASKS_DB = "tasks"
    }
}