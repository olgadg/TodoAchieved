package com.cottondroid.todoachieved.di

import com.cottondroid.todoachieved.TodoApplication
import com.cottondroid.todoachieved.auth.TestAuthenticationModule
import com.cottondroid.todoachieved.database.TestDatabaseModule
import com.cottondroid.todoachieved.network.TestNetworkModule
import com.cottondroid.todoachieved.task.form.TaskFormActivityTest
import com.cottondroid.todoachieved.task.form.TaskFormInjection
import com.cottondroid.todoachieved.task.list.TaskListActivityTest
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
    TestDatabaseModule::class,
    TestAuthenticationModule::class,
    TestNetworkModule::class,
    TaskListInjection::class,
    TaskFormInjection::class
])
interface TestApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TodoApplication): Builder
        fun build(): TestApplicationComponent
    }

    fun inject(app: TodoApplication)
    fun inject(taskFormActivityTest: TaskFormActivityTest)
    fun inject(taskListActivityTest: TaskListActivityTest)
}