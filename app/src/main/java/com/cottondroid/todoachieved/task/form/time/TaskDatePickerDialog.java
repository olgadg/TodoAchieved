package com.cottondroid.todoachieved.task.form.time;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;


public class TaskDatePickerDialog extends DatePickerDialog {

    public TaskDatePickerDialog(Context context, OnDateSetListener listener, Calendar calendar) {
        this(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public TaskDatePickerDialog(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    public TaskDatePickerDialog(Context context, int themeResId, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }
}
