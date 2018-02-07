package com.example.olgadominguez.todoachieved.task.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TodoTask {
    @Id
    private Long id;

    @NotNull
    private String text;
    private Long createdDate;
    private Long date;
    @Generated(hash = 2104567330)
    public TodoTask(Long id, @NotNull String text, Long createdDate, Long date) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
        this.date = date;
    }
    @Generated(hash = 1890195492)
    public TodoTask() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Long getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
    public Long getDate() {
        return this.date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
}
