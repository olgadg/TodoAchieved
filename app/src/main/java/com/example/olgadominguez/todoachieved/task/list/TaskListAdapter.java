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

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private final List<TodoTask> dataSet;
    private final TaskListItemClickListener listener;

    public TaskListAdapter(TaskListItemClickListener listener) {
        this.listener = listener;
        this.dataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_layout, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(dataSet.get(position));
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

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private TaskListItemClickListener listener;
        private long taskId;

        protected ViewHolder(View itemView, TaskListItemClickListener listener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.task_text);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        protected void setData(TodoTask todoTask) {
            textView.setText(todoTask.getText());
            taskId = todoTask.getId();
        }
        @Override
        public void onClick(View v) {
            listener.onItemClick(taskId);
        }
    }
}
