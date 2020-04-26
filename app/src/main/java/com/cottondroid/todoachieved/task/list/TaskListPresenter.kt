package com.cottondroid.todoachieved.task.list

import com.cottondroid.todoachieved.task.TaskRepository
import com.cottondroid.todoachieved.task.model.TodoTask
import com.cottondroid.todoachieved.task.network.TaskUpdateListener
import io.reactivex.Flowable
import javax.inject.Inject

class TaskListPresenter @Inject constructor(
        private val taskRepository: TaskRepository,
        private val taskListener: TaskUpdateListener) {
    val items: Flowable<List<TodoTask>>
        get() = taskRepository.tasks

    fun onResume() {
        taskRepository.registerToTaskUpdates(taskListener)
    }

    fun onLogin() {
        taskRepository.registerToTaskUpdates(taskListener)
    }

    fun onPause() {
        taskRepository.unregisterFromTaskUpdates(taskListener)
    }

}