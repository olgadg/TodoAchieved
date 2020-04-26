package com.cottondroid.todoachieved.task.list

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TaskListInjection {
    @ContributesAndroidInjector
    abstract fun taskListFragment(): TaskListFragment
}