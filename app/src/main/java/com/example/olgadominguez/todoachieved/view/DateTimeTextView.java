package com.example.olgadominguez.todoachieved.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class DateTimeTextView extends TextView {

    public DateTimeTextView(Context context) {
        super(context);
    }

    public DateTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DateTimeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public abstract SimpleDateFormat getDateFormat();

    public void showDateTime(Calendar calendar) {
        setText(getDateFormat().format(calendar.getTimeInMillis()));
    }
}
