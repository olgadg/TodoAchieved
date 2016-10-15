package com.example.olgadominguez.todoachieved.fragment.taskform;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.database.DatabaseHelper;
import com.example.olgadominguez.todoachieved.model.TodoTask;
import com.example.olgadominguez.todoachieved.presenter.taskform.TaskFormPresenter;
import com.example.olgadominguez.todoachieved.presenter.taskform.TaskFormView;

public class TaskFormFragment extends Fragment implements TaskFormView {
    private static final String TAG = "TaskFormFragment";
    private TaskFormPresenter presenter;

    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new TaskFormPresenter(this, DatabaseHelper.getDaoSession(getActivity().getApplication()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_form_fragment_main, container, false);
        rootView.setTag(TAG);

        editText = (EditText) rootView.findViewById(R.id.task_edittext);

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                onSaveButtonClick();
                return true;
            case android.R.id.home :
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void onSaveButtonClick() {
        if (editText.getText() != null) {
            TodoTask todoTask = new TodoTask();
            todoTask.setText(editText.getText().toString());
            presenter.saveTodoTask(todoTask);
        }
    }

    @Override
    public void onItemAdded(TodoTask todoTask) {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
