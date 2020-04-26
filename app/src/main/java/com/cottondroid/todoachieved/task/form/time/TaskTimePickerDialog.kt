package com.cottondroid.todoachieved.task.form.time

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.text.format.DateFormat
import com.cottondroid.todoachieved.R
import java.util.Calendar

class TaskTimePickerDialog(
        context: Context,
        listener: OnTimeSetListener,
        calendar: Calendar
) : TimePickerDialog(
        context,
        listener,
        getDefaultHour(calendar),
        getDefaultMinute(calendar),
        DateFormat.is24HourFormat(context)
) {
    init {
        setButton(
                DialogInterface.BUTTON_NEUTRAL,
                context.getString(R.string.task_form_no_time)
        ) { _, _ -> listener.onTimeSet(null, NO_TIME_HOUR, NO_TIME_MINUTE) }
    }

    companion object {
        private const val NO_TIME_HOUR = 0
        private const val NO_TIME_MINUTE = 0
        private const val DEFAULT_OFFSET = 1
        private const val DEFAULT_HOUR = 12
        private const val DEFAULT_MINUTE = 0
        private fun getDefaultHour(date: Calendar): Int {
            val today = Calendar.getInstance()
            return if (date.after(today)) {
                DEFAULT_HOUR
            } else today[Calendar.HOUR_OF_DAY] + DEFAULT_OFFSET
        }

        private fun getDefaultMinute(date: Calendar): Int {
            val today = Calendar.getInstance()
            return if (date.after(today)) {
                DEFAULT_MINUTE
            } else today[Calendar.MINUTE]
        }
    }
}