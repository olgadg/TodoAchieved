package com.cottondroid.todoachieved.task.form.time;

import android.content.Context;
import android.util.AttributeSet;

import com.cottondroid.todoachieved.R;

import java.text.DateFormat;
import java.util.Calendar;

public class TimeTextView extends DateTimeTextView {

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public DateFormat getDateFormat() {
        return DateFormat.getTimeInstance();
    }

    public void showDateTime(Calendar calendar) {
        if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
            setText(getDefaultText());
        } else {
            super.showDateTime(calendar);
        }
    }

    @Override
    public int getDefaultText() {
        return R.string.task_form_choose_time;
    }

}
