package com.cottondroid.todoachieved.task.list

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cottondroid.todoachieved.R
import com.cottondroid.todoachieved.task.list.AdapterItem.Header
import com.cottondroid.todoachieved.task.list.AdapterItem.Task
import com.cottondroid.todoachieved.task.model.TodoTask
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.task_list_item_header_layout.view.*
import kotlinx.android.synthetic.main.task_list_item_layout.view.*

private const val HEADER_VIEW_TYPE = 1
private const val TASK_VIEW_TYPE = 2

class TaskListAdapter : RecyclerView.Adapter<ViewHolder>() {
    private val dataSet: MutableList<AdapterItem> = ArrayList()
    val taskPublisher = PublishSubject.create<TodoTask>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_VIEW_TYPE -> ViewHolder.HeaderViewHolder(
                    inflater.inflate(R.layout.task_list_item_header_layout, parent, false)
            )
            else -> ViewHolder.TaskViewHolder(
                    inflater.inflate(R.layout.task_list_item_layout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        when (holder) {
            is ViewHolder.HeaderViewHolder -> holder.setData((item as Header).isToday)
            is ViewHolder.TaskViewHolder -> holder.setData(taskPublisher, (item as Task).todoTask)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].viewType
    }

    fun addItems(todoTasks: List<TodoTask>) {
        dataSet.clear()
        todoTasks
                .groupBy { todoTask -> todoTask.date?.let { DateUtils.isToday(it) } ?: true }
                .map { (isToday, taskList) ->
                    dataSet += Header(isToday)
                    dataSet += taskList.map { Task(it) }
                }
    }
}

sealed class AdapterItem(val viewType: Int) {
    data class Header(val isToday: Boolean) : AdapterItem(viewType = HEADER_VIEW_TYPE)
    data class Task(val todoTask: TodoTask) : AdapterItem(viewType = TASK_VIEW_TYPE)
}


sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {

        fun setData(isToday: Boolean) {
            itemView.header.text = itemView.header.resources.getString(
                    if (isToday) {
                        R.string.header_today
                    } else {
                        R.string.header_next
                    }
            )
        }
    }

    class TaskViewHolder(itemView: View) : ViewHolder(itemView) {
        fun setData(taskPublisher: PublishSubject<TodoTask>, todoTask: TodoTask) {
            itemView.taskText.text = todoTask.text
            itemView.setOnClickListener { taskPublisher.onNext(todoTask) }
        }
    }
}
