package com.cottondroid.todoachieved.task.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class TodoTaskDao {
    @Query("SELECT * FROM todotask ORDER BY createdDate")
    abstract fun list(): Flowable<List<TodoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSync(task: TodoTask): Long
    fun insertOrReplace(todoTask: TodoTask): Single<Long> {
        return Single.fromCallable {
            if (todoTask.id == null && todoTask.serverId != null) {
                val id = getIdByServerId(todoTask.serverId)
                todoTask.copy(id = id)
            }
            insertSync(todoTask)
        }
    }

    @Update
    abstract fun updateSync(task: TodoTask): Int
    fun update(todoTask: TodoTask): Single<Int> {
        return Single.fromCallable {
            if (todoTask.id == null) {
                val id = getIdByServerId(todoTask.serverId!!)
                todoTask.copy(id = id)
            }
            updateSync(todoTask)
        }
    }

    @Delete
    abstract fun deleteSync(task: TodoTask): Int
    fun delete(todoTask: TodoTask): Single<Int> {
        return Single.fromCallable {
            if (todoTask.id == null) {
                val id = getIdByServerId(todoTask.serverId!!)
                todoTask.copy(id = id)
            }
            deleteSync(todoTask)
        }
    }

    @Query("SELECT id FROM todotask WHERE serverId = :serverId")
    abstract fun getIdByServerId(serverId: String): Long

    @Query("SELECT * FROM todotask WHERE id = :taskId")
    abstract fun load(taskId: Long): Single<TodoTask>

    @get:Query("SELECT serverId FROM todotask ORDER BY serverCreatedTimestamp desc LIMIT 1")
    abstract val lastServerId: Maybe<String>
}