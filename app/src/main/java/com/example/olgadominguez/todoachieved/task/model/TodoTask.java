package com.example.olgadominguez.todoachieved.task.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TodoTask {
    @PrimaryKey
    private Long id;

    @NonNull
    private String text;
    private Long createdDate;
    private Long date;

    @Ignore
    public TodoTask(Long id, @NonNull String text, Long createdDate, Long date) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
        this.date = date;
    }

    public TodoTask() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
