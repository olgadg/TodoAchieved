package com.cottondroid.todoachieved.task.form.time;


import android.content.Context;
import android.util.AttributeSet;

import com.cottondroid.todoachieved.R;

import java.text.DateFormat;

public class DateTextView extends DateTimeTextView {

    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public DateFormat getDateFormat() {
        return DateFormat.getDateInstance();
    }

    @Override
    public int getDefaultText() {
        return R.string.task_form_choose_date;
    }
}
