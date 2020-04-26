package com.cottondroid.todoachieved.task.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.cottondroid.todoachieved.R

class TaskFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_form_activity_main)
        val customToolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(customToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val INTENT_TASK_ID = "intent_task_id"
    }
}