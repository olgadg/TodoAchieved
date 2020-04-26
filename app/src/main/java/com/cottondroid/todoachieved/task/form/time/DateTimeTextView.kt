package com.cottondroid.todoachieved.task.form.time

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.text.DateFormat
import java.util.Calendar

abstract class DateTimeTextView(
        context: Context,
        attrs: AttributeSet
) : AppCompatTextView(context, attrs) {
    abstract val dateFormat: DateFormat
    abstract val defaultText: Int
    open fun showDateTime(calendar: Calendar) {
        text = dateFormat.format(calendar.time)
    }

    fun showDefaultText() {
        setText(defaultText)
    }
}