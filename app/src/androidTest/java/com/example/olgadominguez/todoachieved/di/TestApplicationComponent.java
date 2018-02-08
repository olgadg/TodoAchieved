package com.example.olgadominguez.todoachieved.di;

import com.example.olgadominguez.todoachieved.TodoApplication;
import com.example.olgadominguez.todoachieved.database.TestDatabaseModule;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivityTest;
import com.example.olgadominguez.todoachieved.task.form.TaskFormInjection;
import com.example.olgadominguez.todoachieved.task.list.TaskListInjection;
import com.example.olgadominguez.todoachieved.tasklist.TaskListActivityTest;

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
