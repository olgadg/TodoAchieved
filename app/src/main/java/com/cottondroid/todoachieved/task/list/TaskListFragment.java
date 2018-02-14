package com.cottondroid.todoachieved.task.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cottondroid.todoachieved.R;
import com.cottondroid.todoachieved.auth.AuthenticationLauncher;
import com.cottondroid.todoachieved.auth.AuthenticationRepository;
import com.cottondroid.todoachieved.task.form.TaskFormActivity;
import com.cottondroid.todoachieved.task.model.TodoTask;

import javax.inject.Inject;
import java.util.List;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.ResourceSubscriber;

import static android.app.Activity.RESULT_OK;
import static com.cottondroid.todoachieved.auth.AuthenticationLauncher.RC_SIGN_IN;

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";

    @Inject
    TaskListPresenter presenter;
    @Inject
    AuthenticationLauncher authenticationLauncher;
    @Inject
    AuthenticationRepository authenticationRepository;

    private TaskListAdapter adapter;
    private CompositeDisposable listDisposable = new CompositeDisposable();
    private Disposable clickDisposable = EmptyDisposable.INSTANCE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        listDisposable = new CompositeDisposable();
        ResourceSubscriber<List<TodoTask>> resourceSubscriber = presenter.getItems().subscribeWith(new ResourceSubscriber<List<TodoTask>>() {
            @Override
            public void onNext(List<TodoTask> todoTasks) {
                adapter.addItems(todoTasks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        listDisposable.add(resourceSubscriber);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listDisposable.dispose();
        clickDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_list_menu, menu);
        if (authenticationRepository.isLoggedIn()) {
            menu.removeItem(R.id.login);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivityForResult(authenticationLauncher.startLoginActivityIntent(), RC_SIGN_IN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK && getActivity() != null) {
            getActivity().invalidateOptionsMenu();
            presenter.onLogin();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        startActivity(intent);
    }

    private void onItemClick(TodoTask todoTask) {
        Intent intent = new Intent(getActivity(), TaskFormActivity.class);
        intent.putExtra(TaskFormActivity.INTENT_TASK_ID, todoTask.getId());
        startActivity(intent);
    }
}
