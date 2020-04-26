package com.cottondroid.todoachieved.task.form

import com.cottondroid.todoachieved.task.TaskRepository
import com.cottondroid.todoachieved.task.model.TodoTask
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import java.util.Calendar
import javax.inject.Inject

class TaskFormPresenter @Inject constructor(private val taskRepository: TaskRepository) {
    val datePublisher = PublishSubject.create<Calendar?>()
    private var todoTask: TodoTask = TodoTask()
    internal var taskDate: Calendar = Calendar.getInstance()
        private set

    fun saveTodoTask(taskName: String?): Single<TodoTask> {
        return saveTodoTask(todoTask.copy(
                text = taskName,
                date = taskDate.timeInMillis
        ))
    }

    private fun saveTodoTask(todoTask: TodoTask): Single<TodoTask> {
        return taskRepository.saveTask(todoTask)
    }

    fun loadTodoTask(taskId: Long): Single<TodoTask> {
        return taskRepository.loadTask(taskId)
                .map { task ->
                    setTodoTask(task)
                    task
                }
    }

    private fun setTodoTask(loadedTodoTask: TodoTask) {
        todoTask = loadedTodoTask
        if (todoTask.date != null) {
            taskDate = Calendar.getInstance()
            taskDate.timeInMillis = todoTask.date!!
        }
    }

    fun chooseToday() {
        val instance = Calendar.getInstance()
        chooseDate(instance[Calendar.YEAR], instance[Calendar.MONTH], instance[Calendar.DAY_OF_MONTH])
    }

    fun chooseDate(year: Int, month: Int, dayOfTheMonth: Int) {
        taskDate[year, month] = dayOfTheMonth
        datePublisher.onNext(taskDate)
    }

    fun chooseTime(hour: Int, minute: Int) {
        taskDate[Calendar.HOUR_OF_DAY] = hour
        taskDate[Calendar.MINUTE] = minute
        taskDate[Calendar.SECOND] = 0
        datePublisher.onNext(taskDate)
    }

}