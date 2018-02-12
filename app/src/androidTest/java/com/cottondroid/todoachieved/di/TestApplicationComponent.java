package com.cottondroid.todoachieved.di;

import com.cottondroid.todoachieved.TodoApplication;
import com.cottondroid.todoachieved.auth.TestAuthenticationModule;
import com.cottondroid.todoachieved.database.TestDatabaseModule;
import com.cottondroid.todoachieved.task.form.TaskFormActivityTest;
import com.cottondroid.todoachieved.task.form.TaskFormInjection;
import com.cottondroid.todoachieved.task.list.TaskListActivityTest;
import com.cottondroid.todoachieved.task.list.TaskListInjection;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        TestDatabaseModule.class,
        TestAuthenticationModule.class,
        TaskListInjection.class,
        TaskFormInjection.class,
})
public interface TestApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(TodoApplication application);

        TestApplicationComponent build();
    }

    void inject(TodoApplication app);

    void inject(TaskFormActivityTest taskFormActivityTest);

    void inject(TaskListActivityTest taskListActivityTest);
}
