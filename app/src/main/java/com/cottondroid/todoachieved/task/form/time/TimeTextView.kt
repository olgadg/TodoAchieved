package com.cottondroid.todoachieved.task.form.time

import android.content.Context
import android.util.AttributeSet
import com.cottondroid.todoachieved.R
import java.text.DateFormat
import java.util.Calendar

class TimeTextView(context: Context, attrs: AttributeSet) : DateTimeTextView(context, attrs) {
    override val dateFormat: DateFormat
        get() = DateFormat.getTimeInstance()

    override fun showDateTime(calendar: Calendar) {
        if (calendar[Calendar.HOUR_OF_DAY] == 0 && calendar[Calendar.MINUTE] == 0) {
            setText(defaultText)
        } else {
            super.showDateTime(calendar)
        }
    }

    override val defaultText: Int
        get() = R.string.task_form_choose_time
}