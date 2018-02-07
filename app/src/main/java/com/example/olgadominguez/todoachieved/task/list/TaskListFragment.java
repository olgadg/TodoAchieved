package com.example.olgadominguez.todoachieved.task.list;

import android.app.Activity;
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
import com.example.olgadominguez.todoachieved.task.list.TaskListAdapter;
import com.example.olgadominguez.todoachieved.task.list.TaskListItemClickListener;
import com.example.olgadominguez.todoachieved.database.DatabaseHelper;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.list.TaskListPresenter;
import com.example.olgadominguez.todoachieved.task.list.TaskListView;

import java.util.List;

public class TaskListFragment extends Fragment implements TaskListView, TaskListItemClickListener {
    private static final String TAG = "TaskListFragment";

    private RecyclerView recyclerView;
    private TaskListAdapter adapter;
    private TaskListPresenter presenter;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskListPresenter(this, DatabaseHelper.getDaoSession(getActivity().getApplication()));
        presenter.getItems();
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


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addTodoTask();
            }
        });


        return rootView;
    }

    @Override
    public void showTasks(List<TodoTask> todoTasks) {
        adapter.addItems(todoTasks);
    }

    @Override
    public void onFinishLoading() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddTodoTask() {
        Intent intent = new Intent(getActivity(), TaskFormActivity.class);
        startActivityForResult(intent, TaskFormActivity.TASK_REQUEST_CODE);
    }

    @Override
    public void onItemClick(long taskId) {
        Intent intent = new Intent(getActivity(), TaskFormActivity.class);
        intent.putExtra(TaskFormActivity.INTENT_TASK_ID, taskId);
        startActivityForResult(intent, TaskFormActivity.TASK_REQUEST_CODE);

    }
}
