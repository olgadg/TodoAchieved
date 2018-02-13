package com.cottondroid.todoachieved.task.model;

import android.arch.persistence.room.TypeConverter;

import java.util.HashMap;
import java.util.Map;

public class ServerCreatedTimestampConverter {
    private static final String SERVER_CREATED_TIMESTAMP_KEY = "serverCreatedTimestamp";

    @TypeConverter
    public static Map<String, String> toServerTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        Map<String, String> timestampMap = new HashMap<>();
        timestampMap.put(SERVER_CREATED_TIMESTAMP_KEY, String.valueOf(value));
        return timestampMap;
    }

    @TypeConverter
    public static Long fromServerTimestamp(Map<String, String> serverTimestamp) {
        try {
            return Long.valueOf(serverTimestamp.get(SERVER_CREATED_TIMESTAMP_KEY));
        } catch (Exception e) {
            return null;
        }
    }
}
