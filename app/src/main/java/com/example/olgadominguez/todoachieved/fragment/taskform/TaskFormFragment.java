package com.example.olgadominguez.todoachieved.fragment.taskform;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        presenter = new TaskFormPresenter(this, DatabaseHelper.getDaoSession(getActivity().getApplication()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_form_fragment_main, container, false);
        rootView.setTag(TAG);

        editText = (EditText) rootView.findViewById(R.id.task_edittext);

        Button saveButton = (Button) rootView.findViewById(R.id.save_task_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick(v);
            }
        });
        return rootView;
    }

    private void onSaveButtonClick(View v) {
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
