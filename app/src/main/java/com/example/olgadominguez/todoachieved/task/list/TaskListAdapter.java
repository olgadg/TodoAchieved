package com.example.olgadominguez.todoachieved.task.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private final List<TodoTask> dataSet;
    private final PublishSubject<TodoTask> taskPublisher = PublishSubject.create();

    public TaskListAdapter() {
        this.dataSet = new ArrayList<>();
    }

    public PublishSubject<TodoTask> getTaskPublisher() {
        return taskPublisher;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(taskPublisher, dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public long getItemId(int position) {
        if (position < dataSet.size() && dataSet.get(position) != null) {
            return dataSet.get(position).getId();
        }
        return -1;
    }

    public void addItems(List<TodoTask> todoTasks) {
        dataSet.clear();
        dataSet.addAll(todoTasks);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        protected ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.task_text);
        }

        protected void setData(final PublishSubject<TodoTask> taskPublisher, final TodoTask todoTask) {
            textView.setText(todoTask.getText());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskPublisher.onNext(todoTask);
                }
            });
        }
    }
}
