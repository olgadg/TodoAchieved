package com.example.olgadominguez.todoachieved.task.form;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.olgadominguez.todoachieved.R;

public class TaskFormActivity extends AppCompatActivity {
    public static final int TASK_REQUEST_CODE = 0;
    public static final String INTENT_TASK_ID = "intent_task_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form_activity_main);
        Toolbar customToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(customToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
