package com.example.olgadominguez.todoachieved.view;


import android.content.Context;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTextView extends DateTimeTextView {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ROOT);

    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }
}
