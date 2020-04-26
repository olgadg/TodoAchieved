package com.cottondroid.todoachieved.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity
data class TodoTask(
        @PrimaryKey(autoGenerate = true)
        @Exclude
        val id: Long? = null,
        val serverId: String? = null,
        val text: String? = null,
        val createdDate: Long? = null,
        val date: Long? = null,
        val serverCreatedTimestamp: Long? = null,
        val serverUpdatedTimestamp: Long? = null
)