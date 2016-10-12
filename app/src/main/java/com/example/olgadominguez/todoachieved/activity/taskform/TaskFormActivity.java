package com.example.olgadominguez.todoachieved.activity.taskform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.olgadominguez.todoachieved.R;

public class TaskFormActivity extends AppCompatActivity {
    public static final int TASK_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form_activity_main);

    }
}
