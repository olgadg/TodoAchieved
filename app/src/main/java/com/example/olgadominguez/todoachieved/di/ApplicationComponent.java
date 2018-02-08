package com.example.olgadominguez.todoachieved.di;

import com.example.olgadominguez.todoachieved.TodoApplication;
import com.example.olgadominguez.todoachieved.database.DatabaseModule;
import com.example.olgadominguez.todoachieved.task.form.TaskFormInjection;
import com.example.olgadominguez.todoachieved.task.list.TaskListInjection;

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
        DatabaseModule.class,
        TaskListInjection.class,
        TaskFormInjection.class,
})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(TodoApplication application);

        ApplicationComponent build();
    }

    void inject(TodoApplication app);
}
