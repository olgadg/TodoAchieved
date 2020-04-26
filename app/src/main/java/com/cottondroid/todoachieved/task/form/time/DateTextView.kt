package com.cottondroid.todoachieved.task.form.time

import android.content.Context
import android.util.AttributeSet
import com.cottondroid.todoachieved.R
import java.text.DateFormat

class DateTextView(context: Context, attrs: AttributeSet) : DateTimeTextView(context, attrs) {
    override val dateFormat: DateFormat = DateFormat.getDateInstance()
    override val defaultText: Int = R.string.task_form_choose_date
}