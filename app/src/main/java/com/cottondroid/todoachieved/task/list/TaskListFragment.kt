package com.cottondroid.todoachieved.task.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cottondroid.todoachieved.R
import com.cottondroid.todoachieved.auth.AuthenticationLauncher
import com.cottondroid.todoachieved.auth.AuthenticationRepository
import com.cottondroid.todoachieved.task.form.TaskFormActivity
import com.cottondroid.todoachieved.task.model.TodoTask
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import kotlinx.android.synthetic.main.task_list_fragment_main.*
import javax.inject.Inject

class TaskListFragment : Fragment() {
    @Inject
    lateinit var presenter: TaskListPresenter

    @Inject
    lateinit var authenticationLauncher: AuthenticationLauncher

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    private val adapter = TaskListAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        val resourceSubscriber: ResourceSubscriber<List<TodoTask>> = presenter.items.subscribeWith(object : ResourceSubscriber<List<TodoTask>>() {
            override fun onNext(todoTasks: List<TodoTask>) {
                adapter.addItems(todoTasks)
                adapter.notifyDataSetChanged()
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
        disposables.add(resourceSubscriber)
    }

    override fun onDetach() {
        super.onDetach()
        disposables.clear()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_list_menu, menu)
        if (authenticationRepository.isLoggedIn) {
            menu.removeItem(R.id.login)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login -> {
                startActivityForResult(authenticationLauncher.startLoginActivityIntent(), AuthenticationLauncher.RC_SIGN_IN)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AuthenticationLauncher.RC_SIGN_IN
                && resultCode == Activity.RESULT_OK && activity != null
        ) {
            requireActivity().invalidateOptionsMenu()
            presenter.onLogin()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.task_list_fragment_main, container, false)
        rootView.tag = TAG
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        disposables.add(
                adapter.taskPublisher.subscribe { todoTask ->
                    onItemClick(todoTask)
                }
        )
        fab.setOnClickListener { onAddTodoTask() }
    }

    private fun onAddTodoTask() {
        val intent = Intent(activity, TaskFormActivity::class.java)
        startActivity(intent)
    }

    private fun onItemClick(todoTask: TodoTask) {
        val intent = Intent(activity, TaskFormActivity::class.java)
        intent.putExtra(TaskFormActivity.INTENT_TASK_ID, todoTask.id)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "TaskListFragment"
    }
}