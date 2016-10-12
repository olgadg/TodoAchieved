package com.example.olgadominguez.todoachieved.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TodoTask {
    @Id
    private Long id;

    @NotNull
    private String text;
    private Date createdDate;
    private Date date;
    @Generated(hash = 1464874962)
    public TodoTask(Long id, @NotNull String text, Date createdDate, Date date) {
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
    public Date getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
}
