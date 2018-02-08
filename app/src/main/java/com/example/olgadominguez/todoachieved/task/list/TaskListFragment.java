package com.example.olgadominguez.todoachieved.task.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivity;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import javax.inject.Inject;
import java.util.List;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";

    @Inject
    TaskListPresenter presenter;

    private TaskListAdapter adapter;
    private Disposable listDisposable = EmptyDisposable.INSTANCE;
    private Disposable clickDisposable = EmptyDisposable.INSTANCE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        listDisposable = presenter.getItems().subscribeWith(new DisposableSingleObserver<List<TodoTask>>() {
            @Override
            public void onSuccess(List<TodoTask> todoTasks) {
                adapter.addItems(todoTasks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listDisposable.dispose();
        clickDisposable.dispose();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TaskFormActivity.TASK_REQUEST_CODE) {
            presenter.getItems();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_list_fragment_main, container, false);
        rootView.setTag(TAG);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TaskListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.getTaskPublisher().subscribeWith(new DisposableObserver<TodoTask>() {
            @Override
            public void onNext(TodoTask todoTask) {
                onItemClick(todoTask);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        FloatingActionButton fab = rootView.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTodoTask();
            }
        });


        return rootView;
    }

    private void onAddTodoTask() {
        Intent intent = new Intent(getActivity(), TaskFormActivity.class);
        startActivityForResult(intent, TaskFormActivity.TASK_REQUEST_CODE);
    }

    private void onItemClick(TodoTask todoTask) {
        Intent intent = new Intent(getActivity(), TaskFormActivity.class);
        intent.putExtra(TaskFormActivity.INTENT_TASK_ID, todoTask.getId());
        startActivityForResult(intent, TaskFormActivity.TASK_REQUEST_CODE);
    }
}
