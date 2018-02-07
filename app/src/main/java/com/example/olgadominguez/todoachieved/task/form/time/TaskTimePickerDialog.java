package com.example.olgadominguez.todoachieved.task.form.time;

import android.app.TimePickerDialog;
import android.content.Context;

import java.util.Calendar;


public class TaskTimePickerDialog extends TimePickerDialog {


    public TaskTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener, Calendar calendar) {
        this(context, listener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
    }

    public TaskTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }

    public TaskTimePickerDialog(Context context, int themeResId, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, themeResId, listener, hourOfDay, minute, is24HourView);
    }
}
