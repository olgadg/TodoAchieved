package com.cottondroid.todoachieved.task.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.cottondroid.todoachieved.R

class TaskListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list_activity_main)
        val customToolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(customToolbar)
    }
}