package com.cottondroid.todoachieved.task.form

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TaskFormInjection {
    @ContributesAndroidInjector
    abstract fun bindTaskFormFragment(): TaskFormFragment
}