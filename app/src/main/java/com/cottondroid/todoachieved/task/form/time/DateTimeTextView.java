package com.cottondroid.todoachieved.task.form.time;

import android.content.Context;
import android.util.AttributeSet;

import java.text.DateFormat;
import java.util.Calendar;

public abstract class DateTimeTextView extends androidx.appcompat.widget.AppCompatTextView {

    public DateTimeTextView(Context context) {
        super(context);
    }

    public DateTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract DateFormat getDateFormat();

    public abstract int getDefaultText();

    public void showDateTime(Calendar calendar) {
        setText(getDateFormat().format(calendar.getTime()));
    }

    public void showDefaultText() {
        setText(getDefaultText());
    }
}
