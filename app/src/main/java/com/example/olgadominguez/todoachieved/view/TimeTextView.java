package com.example.olgadominguez.todoachieved.view;


import android.content.Context;
import android.util.AttributeSet;

import com.example.olgadominguez.todoachieved.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeTextView extends DateTimeTextView {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.ROOT);

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    @Override
    public int getDefaultText() {
        return R.string.task_form_choose_time;
    }

}
