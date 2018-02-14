package com.cottondroid.todoachieved.task.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Map;

@Entity
public class TodoTask {

    @PrimaryKey(autoGenerate = true)
    @Exclude
    private Long id;

    @NonNull
    private String text;
    private Long createdDate;
    private Long date;
    @Exclude
    private String serverId;
    @TypeConverters(ServerCreatedTimestampConverter.class)
    private Map<String, String> serverCreatedTimestamp;
    @TypeConverters(ServerUpdatedTimestampConverter.class)
    private Map<String, String> serverUpdatedTimestamp;

    @Ignore
    public TodoTask(Long id, @NonNull String text, Long createdDate, Long date) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
        this.date = date;
    }

    public TodoTask() {

    }

    @Exclude
    public Long getId() {
        return id;
    }

    @Exclude
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

    @Exclude
    public String getServerId() {
        return serverId;
    }

    @Exclude
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Map<String, String> getServerCreatedTimestamp() {
        return serverCreatedTimestamp;
    }

    @Exclude
    public void setServerCreatedTimestamp(Map<String, String> serverCreatedTimestamp) {
        this.serverCreatedTimestamp = serverCreatedTimestamp;
    }

    @SuppressWarnings("unused")
    public void setServerCreatedTimestamp(Long serverCreatedTimestamp) {
        this.serverCreatedTimestamp = ServerCreatedTimestampConverter.toServerTimestamp(serverCreatedTimestamp);
    }

    public Map<String, String> getServerUpdatedTimestamp() {
        return serverUpdatedTimestamp;
    }

    @Exclude
    public void setServerUpdatedTimestamp(Map<String, String> serverUpdatedTimestamp) {
        this.serverUpdatedTimestamp = serverUpdatedTimestamp;
    }

    @SuppressWarnings("unused")
    public void setServerUpdatedTimestamp(Long serverUpdatedTimestamp) {
        this.serverUpdatedTimestamp = ServerUpdatedTimestampConverter.toServerTimestamp(serverUpdatedTimestamp);
    }
}
