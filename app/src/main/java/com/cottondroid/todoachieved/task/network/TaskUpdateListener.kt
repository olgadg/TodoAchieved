package com.cottondroid.todoachieved.task.network

import android.util.Log
import com.cottondroid.todoachieved.task.model.TodoTask
import com.cottondroid.todoachieved.task.model.TodoTaskDao
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskUpdateListener @Inject constructor(private val taskDao: TodoTaskDao) : ChildEventListener {
    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
        val todoTask = fromSnapshot(dataSnapshot)
        if (todoTask != null) {
            taskDao.insertOrReplace(todoTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
        val todoTask = fromSnapshot(dataSnapshot)
        if (todoTask != null) {
            taskDao.update(todoTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        val todoTask = dataSnapshot.getValue(TodoTask::class.java)
        if (todoTask != null) {
            taskDao.delete(todoTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }

    override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
        //No action
    }

    override fun onCancelled(databaseError: DatabaseError) {
        Log.w(TAG, "Cancelled", databaseError.toException())
    }

    private fun fromSnapshot(taskSnapshot: DataSnapshot): TodoTask? {
        val todoTask = taskSnapshot.getValue(TodoTask::class.java)
        todoTask?.copy(serverId = taskSnapshot.key)
        return todoTask
    }

    companion object {
        private val TAG = TaskUpdateListener::class.java.simpleName
    }

}