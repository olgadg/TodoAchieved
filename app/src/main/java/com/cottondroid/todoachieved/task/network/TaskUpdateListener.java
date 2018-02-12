package com.cottondroid.todoachieved.task.network;

import android.util.Log;

import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;

import static com.cottondroid.todoachieved.di.ApplicationModule.IO_SCHEDULER;
import static com.cottondroid.todoachieved.di.ApplicationModule.UI_SCHEDULER;

public class TaskUpdateListener implements ChildEventListener, ValueEventListener {

    private static final String TAG = TaskUpdateListener.class.getSimpleName();
    private final TodoTaskDao taskDao;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    @Inject
    public TaskUpdateListener(TodoTaskDao taskDao,
                              @Named(UI_SCHEDULER) Scheduler uiScheduler,
                              @Named(IO_SCHEDULER) Scheduler ioScheduler) {
        this.taskDao = taskDao;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
        final TodoTask todoTask = dataSnapshot.getValue(TodoTask.class);
        taskDao.insert(todoTask)
                .subscribeOn(ioScheduler).observeOn(uiScheduler).subscribe();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
        final TodoTask todoTask = dataSnapshot.getValue(TodoTask.class);
        taskDao.update(todoTask)
                .subscribeOn(ioScheduler).observeOn(uiScheduler).subscribe();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        final TodoTask todoTask = dataSnapshot.getValue(TodoTask.class);
        taskDao.delete(todoTask)
                .subscribeOn(ioScheduler).observeOn(uiScheduler).subscribe();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
        //No action
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(TAG, "Cancelled", databaseError.toException());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<TodoTask> todoTasks = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            TodoTask todoTask = postSnapshot.getValue(TodoTask.class);
            todoTasks.add(todoTask);
        }
        taskDao.insert(todoTasks)
                .subscribeOn(ioScheduler).observeOn(uiScheduler).subscribe();
    }
}
