package com.cottondroid.todoachieved.task.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public abstract class TodoTaskDao {
    @Query("SELECT * FROM todotask ORDER BY createdDate")
    public abstract Flowable<List<TodoTask>> list();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertSync(TodoTask task);

    public Single<Long> insertOrReplace(final TodoTask todoTask) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                if (todoTask.getId() == null && todoTask.getServerId() != null) {
                    Long id = getIdByServerId(todoTask.getServerId());
                    todoTask.setId(id);
                }
                return insertSync(todoTask);
            }
        });
    }

    @Update
    public abstract int updateSync(TodoTask task);

    public Single<Integer> update(final TodoTask todoTask) {
        return Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if (todoTask.getId() == null) {
                    Long id = getIdByServerId(todoTask.getServerId());
                    todoTask.setId(id);
                }
                return updateSync(todoTask);
            }
        });
    }

    @Delete
    public abstract int deleteSync(TodoTask task);

    public Single<Integer> delete(final TodoTask todoTask) {
        return Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if (todoTask.getId() == null) {
                    Long id = getIdByServerId(todoTask.getServerId());
                    todoTask.setId(id);
                }
                return deleteSync(todoTask);
            }
        });
    }

    @Query("SELECT id FROM todotask WHERE serverId = :serverId")
    public abstract Long getIdByServerId(String serverId);

    @Query("SELECT * FROM todotask WHERE id = :taskId")
    public abstract Single<TodoTask> load(long taskId);

    @Query("SELECT serverId FROM todotask ORDER BY serverCreatedTimestamp desc LIMIT 1")
    public abstract Maybe<String> getLastServerId();

}
