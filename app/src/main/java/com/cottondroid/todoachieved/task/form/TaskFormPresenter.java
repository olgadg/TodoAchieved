package com.cottondroid.todoachieved.task.form;

import com.cottondroid.todoachieved.task.TaskRepository;
import com.cottondroid.todoachieved.task.model.TodoTask;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

import static com.cottondroid.todoachieved.di.ApplicationModule.IO_SCHEDULER;
import static com.cottondroid.todoachieved.di.ApplicationModule.UI_SCHEDULER;

public class TaskFormPresenter {

    private final TaskRepository taskRepository;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;
    private final PublishSubject<Calendar> datePublisher = PublishSubject.create();

    private TodoTask todoTask;
    private Calendar taskDate;

    @Inject
    public TaskFormPresenter(TaskRepository taskRepository,
                             @Named(UI_SCHEDULER) Scheduler uiScheduler,
                             @Named(IO_SCHEDULER) Scheduler ioScheduler) {
        this.taskRepository = taskRepository;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    public Single<TodoTask> saveTodoTask(String taskName) {
        if (todoTask == null) {
            todoTask = new TodoTask();
            todoTask.setCreatedDate(Calendar.getInstance().getTimeInMillis());
        }
        todoTask.setText(taskName);
        if (taskDate != null) {
            todoTask.setDate(taskDate.getTimeInMillis());
        }
        return saveTodoTask(todoTask);
    }

    private Single<TodoTask> saveTodoTask(final TodoTask todoTask) {
        return taskRepository.saveTask(todoTask)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public Single<TodoTask> loadTodoTask(final long taskId) {
        return taskRepository.loadTask(taskId)
                .map(new Function<TodoTask, TodoTask>() {
                    @Override
                    public TodoTask apply(TodoTask task) throws Exception {
                        setTodoTask(task);
                        return task;
                    }
                })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    private void setTodoTask(TodoTask loadedTodoTask) {
        todoTask = loadedTodoTask;
        if (todoTask.getDate() != null) {
            taskDate = Calendar.getInstance();
            taskDate.setTimeInMillis(todoTask.getDate());
        }
    }

    public void chooseToday() {
        Calendar instance = Calendar.getInstance();
        chooseDate(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));
    }

    public void chooseDate(int year, int month, int dayOfTheMonth) {
        if (taskDate == null) {
            taskDate = Calendar.getInstance();
        }
        taskDate.set(year, month, dayOfTheMonth);
        datePublisher.onNext(taskDate);
    }

    public void chooseTime(int hour, int minute) {
        taskDate.set(Calendar.HOUR_OF_DAY, hour);
        taskDate.set(Calendar.MINUTE, minute);
        taskDate.set(Calendar.SECOND, 0);
        datePublisher.onNext(taskDate);
    }

    public Calendar getTaskDate() {
        if (taskDate != null) {
            return taskDate;
        }
        return Calendar.getInstance();
    }

    public PublishSubject<Calendar> getDatePublisher() {
        return datePublisher;
    }
}
