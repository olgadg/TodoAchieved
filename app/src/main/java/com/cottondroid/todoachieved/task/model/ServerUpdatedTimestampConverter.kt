package com.cottondroid.todoachieved.task.model

import androidx.room.TypeConverter
import java.util.HashMap

class ServerUpdatedTimestampConverter {
    companion object {
        private const val SERVER_UPDATED_TIMESTAMP_KEY = "serverUpdatedTimestamp"

        @JvmStatic
        @TypeConverter
        fun toServerUpdatedTimestamp(value: Long?): Map<String, String>? {
            if (value == null) {
                return null
            }
            val timestampMap: MutableMap<String, String> = HashMap()
            timestampMap[SERVER_UPDATED_TIMESTAMP_KEY] = value.toString()
            return timestampMap
        }

        @JvmStatic
        @TypeConverter
        fun fromServerUpdatedTimestamp(serverTimestamp: Map<String?, String?>): Long? {
            return serverTimestamp[SERVER_UPDATED_TIMESTAMP_KEY]?.toLong()
        }
    }
}