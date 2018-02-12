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
import io.reactivex.Single;

@Dao
public abstract class TodoTaskDao {
    @Query("SELECT * FROM todotask ORDER BY createdDate")
    public abstract Flowable<List<TodoTask>> list();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertSync(TodoTask task);

    public Single<Long> insert(final TodoTask todoTask) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return insertSync(todoTask);
            }
        });
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long[] insertSync(List<TodoTask> tasks);

    public Single<Long[]> insert(final List<TodoTask> tasks) {
        return Single.fromCallable(new Callable<Long[]>() {
            @Override
            public Long[] call() throws Exception {
                return insertSync(tasks);
            }
        });
    }

    @Update
    public abstract int updateSync(TodoTask task);

    public Single<Integer> update(final TodoTask todoTask) {
        return Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
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
                return deleteSync(todoTask);
            }
        });
    }

    @Query("SELECT * FROM todotask WHERE id = :taskId")
    public abstract Single<TodoTask> load(long taskId);
}
