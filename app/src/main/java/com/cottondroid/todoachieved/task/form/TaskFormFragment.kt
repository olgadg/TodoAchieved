package com.cottondroid.todoachieved.task.form

import android.app.Activity
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cottondroid.todoachieved.R
import com.cottondroid.todoachieved.task.form.time.TaskDatePickerDialog
import com.cottondroid.todoachieved.task.form.time.TaskTimePickerDialog
import com.cottondroid.todoachieved.task.model.TodoTask
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.pick_date_layout.*
import kotlinx.android.synthetic.main.task_form_fragment_main.*
import java.util.Calendar
import javax.inject.Inject

class TaskFormFragment : Fragment() {
    @Inject
    lateinit var presenter: TaskFormPresenter
    private val disposables = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        disposables.add(
                presenter.datePublisher.subscribe
                { taskDate ->
                    onDateTimeChanged(taskDate)
                }
        )
        val taskId = (context as Activity).intent.getLongExtra(TaskFormActivity.INTENT_TASK_ID, -1)
        if (taskId != -1L) {
            disposables.add(presenter.loadTodoTask(taskId).subscribe(
                    { task ->
                        onTaskLoaded(task)
                    },
                    { e ->
                        onErrorLoadingTask(e)
                    }
            ))
        }
    }

    override fun onDetach() {
        super.onDetach()
        disposables.clear()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(
                R.layout.task_form_fragment_main,
                container,
                false
        )
        rootView.tag = TAG
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskEditText.requestFocus()
        dateTextView.setOnClickListener { chooseDate() }
        timeTextView.setOnClickListener {
            presenter.chooseToday()
            chooseTime()
        }
        dateTextView.showDefaultText()
        timeTextView.showDefaultText()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_form_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.done -> {
                onSaveButtonClick()
                true
            }
            R.id.delete -> {
                onDeleteButtonClick()
                true
            }
            android.R.id.home -> {
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onSaveButtonClick() {
        taskEditText.text.let {
            disposables.add(presenter
                    .saveTodoTask(it.toString())
                    .subscribe(
                            {
                                finish()
                            },
                            { e ->
                                onErrorSavingTask(e)
                            }
                    )
            )
        }
    }


    private fun onDeleteButtonClick() {
            disposables.add(presenter
                    .deleteTodoTask()
                    .subscribe(
                            {
                                finish()
                            },
                            { e ->
                                onErrorDeletingTask(e)
                            }
                    )
            )
    }

    private fun finish() {
        activity?.finish()
    }

    private fun onDateTimeChanged(taskDate: Calendar) {
        dateTextView.showDateTime(taskDate)
        timeTextView.showDateTime(taskDate)
    }

    private fun onTaskLoaded(todoTask: TodoTask) {
        taskEditText?.setText(todoTask.text)
        if (todoTask.date != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = todoTask.date
            dateTextView.showDateTime(calendar)
            timeTextView.showDateTime(calendar)
        } else {
            dateTextView.showDefaultText()
            timeTextView.showDefaultText()
        }
    }

    private fun onErrorSavingTask(e: Throwable) {
        Log.e(TAG, getString(R.string.task_form_saving_error), e)
        showError(R.string.task_form_saving_error)
    }

    private fun onErrorDeletingTask(e: Throwable) {
        Log.e(TAG, getString(R.string.task_form_deleting_error), e)
        showError(R.string.task_form_deleting_error)
    }

    private fun onErrorLoadingTask(e: Throwable) {
        Log.e(TAG, getString(R.string.task_form_loading_error), e)
        showError(R.string.task_form_loading_error)
    }

    private fun showError(messageId: Int) {
        view?.let {
            Snackbar.make(it, messageId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun chooseDate() {
        TaskDatePickerDialog(
                requireActivity(),
                OnDateSetListener { _, year, month, dayOfMonth ->
                    presenter.chooseDate(year, month, dayOfMonth)
                    chooseTime()
                },
                presenter.taskDate
        ).show()
    }

    private fun chooseTime() {
        TaskTimePickerDialog(
                requireActivity(),
                OnTimeSetListener { _, hourOfDay, minute ->
                    presenter.chooseTime(hourOfDay, minute)
                },
                presenter.taskDate
        ).show()
    }

    companion object {
        private const val TAG = "TaskFormFragment"
    }
}