package com.example.olgadominguez.todoachieved.task.form;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.olgadominguez.todoachieved.task.form.time.TaskDatePickerDialog;
import com.example.olgadominguez.todoachieved.task.form.time.TaskTimePickerDialog;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.form.time.DateTextView;
import com.example.olgadominguez.todoachieved.task.form.time.TimeTextView;

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

        taskDateTextView.showDefaultText();
        taskTimeTextView.showDefaultText();
        if (savedInstanceState == null) {
            long taskId = getActivity().getIntent().getLongExtra(TaskFormActivity.INTENT_TASK_ID, -1);
            if (taskId != -1) {
                presenter.loadTodoTask(taskId);
            }
        }
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
            presenter.saveTodoTask(taskNameEditText.getText().toString());
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

    @Override
    public void onTaskLoaded(TodoTask todoTask) {
        taskNameEditText.setText(todoTask.getText());
        if (todoTask.getDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(todoTask.getDate());
            taskDateTextView.showDateTime(calendar);
            taskTimeTextView.showDateTime(calendar);
        } else {
            taskDateTextView.showDefaultText();
            taskTimeTextView.showDefaultText();
        }
    }

    @Override
    public void onErrorSavingTask(Throwable e) {
        Log.e(TAG, getString(R.string.task_form_saving_error), e);
        showError(R.string.task_form_saving_error);
    }

    @Override
    public void onErrorLoadingTask(Throwable e) {
        Log.e(TAG, getString(R.string.task_form_loading_error), e);
        showError(R.string.task_form_loading_error);

    }

    private void showError(int messageId) {
        if (getView() != null) {
            Snackbar.make(getView(), messageId, Snackbar.LENGTH_LONG).show();
        }
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
