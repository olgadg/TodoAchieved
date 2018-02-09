package com.example.olgadominguez.todoachieved.task.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TodoTaskDao {
    @Query("SELECT * FROM todotask ORDER BY createdDate")
    Flowable<List<TodoTask>> list();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TodoTask task);

    @Update
    void update(TodoTask task);

    @Query("SELECT * FROM todotask WHERE id = :taskId")
    Single<TodoTask> load(long taskId);
}
