package com.cottondroid.todoachieved.di

import com.cottondroid.todoachieved.TodoApplication
import com.cottondroid.todoachieved.auth.AuthenticationModule
import com.cottondroid.todoachieved.database.DatabaseModule
import com.cottondroid.todoachieved.network.NetworkModule
import com.cottondroid.todoachieved.task.form.TaskFormInjection
import com.cottondroid.todoachieved.task.list.TaskListInjection
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DatabaseModule::class,
    AuthenticationModule::class,
    NetworkModule::class,
    TaskListInjection::class,
    TaskFormInjection::class
])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TodoApplication): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: TodoApplication)
}