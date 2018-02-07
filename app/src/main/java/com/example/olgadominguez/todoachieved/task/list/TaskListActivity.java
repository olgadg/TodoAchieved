package com.example.olgadominguez.todoachieved.task.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.olgadominguez.todoachieved.R;


public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity_main);
        Toolbar customToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(customToolbar);

    }
}
