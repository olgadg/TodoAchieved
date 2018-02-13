package com.cottondroid.todoachieved.task.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.Collections;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long[] insertSync(List<TodoTask> tasks);

    public Single<Long[]> insertOrUpdate(final List<TodoTask> tasks) {
        return Single.fromCallable(new Callable<Long[]>() {
            @Override
            @Transaction
            public Long[] call() throws Exception {
                return insertOrUpdateSync(tasks);
            }
        });
    }

    @Transaction
    protected Long[] insertOrUpdateSync(final List<TodoTask> tasks) {
        List<TodoTask> toInsert = new ArrayList<>();
        List<TodoTask> toUpdate = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (TodoTask task : tasks) {
            if (task.getId() == null) {
                Long id = getIdByServerId(task.getServerId());
                task.setId(id);
            }
            if (task.getId() == null) {
                toInsert.add(task);
            } else {
                ids.add(task.getId());
                toUpdate.add(task);
            }
        }
        Collections.addAll(ids, insertSync(toInsert));
        updateSync(toUpdate);
        return ids.toArray(new Long[]{});
    }

    @Update
    public abstract int updateSync(TodoTask task);

    @Update
    public abstract void updateSync(List<TodoTask> task);

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
}
