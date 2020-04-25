package com.cottondroid.todoachieved.task.form;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cottondroid.todoachieved.R;
import com.cottondroid.todoachieved.task.form.time.DateTextView;
import com.cottondroid.todoachieved.task.form.time.TaskDatePickerDialog;
import com.cottondroid.todoachieved.task.form.time.TaskTimePickerDialog;
import com.cottondroid.todoachieved.task.form.time.TimeTextView;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class TaskFormFragment extends Fragment {
    private static final String TAG = "TaskFormFragment";

    @Inject
    TaskFormPresenter presenter;

    private Disposable loadDisposable = EmptyDisposable.INSTANCE;
    private Disposable dateDisposable = EmptyDisposable.INSTANCE;
    private Disposable saveDisposable = EmptyDisposable.INSTANCE;

    private EditText taskNameEditText;
    private DateTextView taskDateTextView;
    private TimeTextView taskTimeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        dateDisposable = presenter.getDatePublisher().subscribeWith(new DisposableObserver<Calendar>() {
            @Override
            public void onNext(Calendar taskDate) {
                onDateTimeChanged(taskDate);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        long taskId = ((Activity) context).getIntent().getLongExtra(TaskFormActivity.INTENT_TASK_ID, -1);
        if (taskId != -1) {
            loadDisposable = presenter.loadTodoTask(taskId)
                    .subscribeWith(new DisposableSingleObserver<TodoTask>() {
                        @Override
                        public void onSuccess(TodoTask task) {
                            onTaskLoaded(task);
                        }

                        @Override
                        public void onError(Throwable e) {
                            onErrorLoadingTask(e);
                        }
                    });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loadDisposable.dispose();
        dateDisposable.dispose();
        saveDisposable.dispose();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_form_fragment_main, container, false);
        rootView.setTag(TAG);

        taskNameEditText = rootView.findViewById(R.id.task_edittext);
        taskNameEditText.requestFocus();
        taskDateTextView = rootView.findViewById(R.id.date_textview);
        taskDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        taskTimeTextView = rootView.findViewById(R.id.time_textview);
        taskTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseToday();
                chooseTime();
            }
        });

        taskDateTextView.showDefaultText();
        taskTimeTextView.showDefaultText();
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
            case android.R.id.home:
                if (getActivity() != null) {
                    getActivity().finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void onSaveButtonClick() {
        if (taskNameEditText.getText() != null) {
            saveDisposable = presenter
                    .saveTodoTask(taskNameEditText.getText().toString())
                    .subscribeWith(new DisposableSingleObserver<TodoTask>() {
                        @Override
                        public void onSuccess(TodoTask task) {
                            onItemAdded();
                        }

                        @Override
                        public void onError(Throwable e) {
                            onErrorSavingTask(e);
                        }
                    });
        }
    }

    private void onItemAdded() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    private void onDateTimeChanged(Calendar taskDate) {
        taskDateTextView.showDateTime(taskDate);
        taskTimeTextView.showDateTime(taskDate);
    }

    private void onTaskLoaded(TodoTask todoTask) {
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

    private void onErrorSavingTask(Throwable e) {
        Log.e(TAG, getString(R.string.task_form_saving_error), e);
        showError(R.string.task_form_saving_error);
    }

    private void onErrorLoadingTask(Throwable e) {
        Log.e(TAG, getString(R.string.task_form_loading_error), e);
        showError(R.string.task_form_loading_error);

    }

    private void showError(int messageId) {
        if (getView() != null) {
            Snackbar.make(getView(), messageId, Snackbar.LENGTH_LONG).show();
        }
    }

    private void chooseDate() {
        new TaskDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.chooseDate(year, month, dayOfMonth);
                chooseTime();
            }
        }, presenter.getTaskDate()).show();
    }

    private void chooseTime() {
        new TaskTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                presenter.chooseTime(hourOfDay, minute);
            }
        }, presenter.getTaskDate()).show();
    }
}
