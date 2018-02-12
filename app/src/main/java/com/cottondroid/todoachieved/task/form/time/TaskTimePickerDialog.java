package com.cottondroid.todoachieved.task.form.time;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;

import com.cottondroid.todoachieved.R;

import java.util.Calendar;

public class TaskTimePickerDialog extends TimePickerDialog {

    private static final int NO_TIME_HOUR = 0;
    private static final int NO_TIME_MINUTE = 0;
    private static final int DEFAULT_OFFSET = 1;
    private static final int DEFAULT_HOUR = 12;
    private static final int DEFAULT_MINUTE = 0;

    public TaskTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener, Calendar calendar) {
        this(context, listener, getDefaultHour(calendar), getDefaultMinute(calendar), DateFormat.is24HourFormat(context));
    }

    private static int getDefaultHour(Calendar date) {
        Calendar today = Calendar.getInstance();
        if (date.after(today)) {
            return DEFAULT_HOUR;
        }
        return today.get(Calendar.HOUR_OF_DAY) + DEFAULT_OFFSET;
    }

    private static int getDefaultMinute(Calendar date) {
        Calendar today = Calendar.getInstance();
        if (date.after(today)) {
            return DEFAULT_MINUTE;
        }
        return today.get(Calendar.MINUTE);
    }

    private TaskTimePickerDialog(Context context, final OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
        setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.task_form_no_time), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onTimeSet(null, NO_TIME_HOUR, NO_TIME_MINUTE);
            }
        });
    }
}
