package com.cottondroid.todoachieved.task.network

import com.cottondroid.todoachieved.auth.AuthenticationRepository
import com.cottondroid.todoachieved.network.NetworkModule
import com.cottondroid.todoachieved.task.model.TodoTask
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TodoTaskService @Inject constructor(
        @param:Named(NetworkModule.TASKS_DB)
        private val dbReferenceProvider: Provider<DatabaseReference>,
        private val authenticationRepository: AuthenticationRepository
) {
    fun registerToTaskUpdates(taskListener: TaskUpdateListener, lastServerId: String) {
        if (authenticationRepository.isLoggedIn) {
            val dbReference = dbReferenceProvider.get()
            val query = if (lastServerId.isEmpty()) {
                dbReference.orderByKey().startAt(lastServerId)
            } else {
                dbReference.orderByKey()
            }
            query.addChildEventListener(taskListener)
        }
    }

    fun unregisterFromTaskUpdates(taskListener: TaskUpdateListener) {
        if (authenticationRepository.isLoggedIn) {
            dbReferenceProvider.get().removeEventListener(taskListener)
        }
    }

    fun saveTask(todoTask: TodoTask) {
        if (authenticationRepository.isLoggedIn) {
            dbReferenceProvider.get().child(todoTask.serverId!!).setValue(todoTask)
        }
    }

    fun populateServerValues(todoTask: TodoTask) {
        if (authenticationRepository.isLoggedIn) {
            if (todoTask.serverId == null) {
                todoTask.copy(
                        serverId = dbReferenceProvider.get().push().key,
                        serverCreatedTimestamp = createdTimestamp()
                )
            }
            todoTask.copy(
                    serverUpdatedTimestamp = updatedTimestamp()
            )
        }
    }

    companion object {
        private const val SERVER_CREATED_TIMESTAMP_KEY = "serverCreatedTimestamp"
        private const val SERVER_UPDATED_TIMESTAMP_KEY = "serverUpdatedTimestamp"

        fun createdTimestamp() = ServerValue.TIMESTAMP[SERVER_CREATED_TIMESTAMP_KEY]?.toLong()
        fun updatedTimestamp() = ServerValue.TIMESTAMP[SERVER_UPDATED_TIMESTAMP_KEY]?.toLong()
    }
}