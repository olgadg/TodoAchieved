package com.cottondroid.todoachieved.task.form.time

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

class TaskDatePickerDialog(
        context: Context,
        listener: OnDateSetListener?,
        calendar: Calendar
) : DatePickerDialog(
        context,
        listener,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
)