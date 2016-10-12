package com.example.olgadominguez.todoachieved.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.model.TodoTask;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private final List<TodoTask> dataSet;

    public TaskListAdapter() {
        this.dataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addItems(List<TodoTask> todoTasks) {
        dataSet.clear();
        dataSet.addAll(todoTasks);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        protected ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.task_text);
        }

        protected void setData(TodoTask todoTask) {
            textView.setText(todoTask.getText());
        }
    }
}