package com.cottondroid.todoachieved.task

import com.cottondroid.todoachieved.task.model.TodoTask
import com.cottondroid.todoachieved.task.model.TodoTaskDao
import com.cottondroid.todoachieved.task.network.TaskUpdateListener
import com.cottondroid.todoachieved.task.network.TodoTaskService
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
        private val taskDao: TodoTaskDao,
        private val todoTaskService: TodoTaskService
) {
    private val disposable = CompositeDisposable()
    val tasks: Flowable<List<TodoTask>>
        get() = taskDao.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    fun registerToTaskUpdates(taskListener: TaskUpdateListener) {
        disposable.add(
                taskDao.lastServerId
                        .defaultIfEmpty("")
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe { lastServerId ->
                            todoTaskService.registerToTaskUpdates(taskListener, lastServerId)
                        }
        )
    }

    fun unregisterFromTaskUpdates(taskListener: TaskUpdateListener) {
        todoTaskService.unregisterFromTaskUpdates(taskListener)
        disposable.clear()
    }

    fun saveTask(todoTask: TodoTask): Single<TodoTask> {
        return taskDao.insertOrReplace(todoTask)
                .flatMap { taskId ->
                    taskDao.load(taskId)
                }
                .doOnSuccess { task ->
                    todoTaskService.populateServerValues(task)
                    taskDao.updateSync(task)
                    todoTaskService.saveTask(task)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadTask(taskId: Long): Single<TodoTask> {
        return taskDao.load(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}