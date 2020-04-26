package com.cottondroid.todoachieved.task.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cottondroid.todoachieved.R
import com.cottondroid.todoachieved.task.model.TodoTask
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.task_list_item_layout.view.*

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    private val dataSet: MutableList<TodoTask>
    val taskPublisher = PublishSubject.create<TodoTask>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(taskPublisher, dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemId(position: Int): Long {
        return dataSet[position].id ?: -1
    }

    fun addItems(todoTasks: List<TodoTask>) {
        dataSet.clear()
        dataSet.addAll(todoTasks)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(taskPublisher: PublishSubject<TodoTask?>, todoTask: TodoTask?) {
            itemView.taskText.text = todoTask!!.text
            itemView.setOnClickListener { taskPublisher.onNext(todoTask) }
        }

    }

    init {
        dataSet = ArrayList()
    }
}