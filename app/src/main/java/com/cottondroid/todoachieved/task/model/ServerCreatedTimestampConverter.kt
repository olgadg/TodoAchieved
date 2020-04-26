package com.cottondroid.todoachieved.task.model

import androidx.room.TypeConverter

class ServerCreatedTimestampConverter {

    companion object {
        private const val SERVER_CREATED_TIMESTAMP_KEY = "serverCreatedTimestamp"

        @JvmStatic
        @TypeConverter
        fun toServerCreatedTimestamp(value: Long?): Map<String, String>? {
            if (value == null) {
                return null
            }
            val timestampMap: MutableMap<String, String> = HashMap()
            timestampMap[SERVER_CREATED_TIMESTAMP_KEY] = value.toString()
            return timestampMap
        }

        @JvmStatic
        @TypeConverter
        fun fromServerCreatedTimestamp(serverTimestamp: Map<String?, String?>): Long? {
            return serverTimestamp[SERVER_CREATED_TIMESTAMP_KEY]?.toLong()
        }
    }
}