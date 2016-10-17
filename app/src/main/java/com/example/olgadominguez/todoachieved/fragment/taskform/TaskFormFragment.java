package com.example.olgadominguez.todoachieved.fragment.taskform;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.database.DatabaseHelper;
import com.example.olgadominguez.todoachieved.dialog.TaskDatePickerDialog;
import com.example.olgadominguez.todoachieved.dialog.TaskTimePickerDialog;
import com.example.olgadominguez.todoachieved.model.TodoTask;
import com.example.olgadominguez.todoachieved.presenter.taskform.TaskFormPresenter;
import com.example.olgadominguez.todoachieved.presenter.taskform.TaskFormView;
import com.example.olgadominguez.todoachieved.view.DateTextView;
import com.example.olgadominguez.todoachieved.view.TimeTextView;

import java.util.Calendar;

public class TaskFormFragment extends Fragment implements TaskFormView {
    private static final String TAG = "TaskFormFragment";
    private TaskFormPresenter presenter;

    private EditText taskNameEditText;
    private DateTextView taskDateTextView;
    private TimeTextView taskTimeTextView;

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

        taskNameEditText = (EditText) rootView.findViewById(R.id.task_edittext);
        taskDateTextView = (DateTextView) rootView.findViewById(R.id.date_textview);
        taskDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        taskTimeTextView = (TimeTextView) rootView.findViewById(R.id.time_textview);
        taskTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime();
            }
        });
        presenter.onViewSet();
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
        if (taskNameEditText.getText() != null) {
            TodoTask todoTask = new TodoTask();
            todoTask.setText(taskNameEditText.getText().toString());
            presenter.saveTodoTask(todoTask);
        }
    }

    @Override
    public void onItemAdded(TodoTask todoTask) {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onDateTimeChanged(Calendar taskDate) {
        taskDateTextView.showDateTime(taskDate);
        taskTimeTextView.showDateTime(taskDate);
    }

    public void chooseDate() {
        new TaskDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.chooseDate(year, month, dayOfMonth);
            }
        }, presenter.getTaskDate()).show();
    }

    public void chooseTime() {
        new TaskTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                presenter.chooseTime(hourOfDay, minute);
            }
        }, presenter.getTaskDate()).show();
    }
}
