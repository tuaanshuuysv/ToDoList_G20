package com.todolist.app.presentation.ui.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.todolist.app.R;
import com.todolist.app.data.local.entity.Task;
import java.util.ArrayList;
import java.util.List;

public class KanbanColumnAdapter extends RecyclerView.Adapter<KanbanColumnAdapter.TaskViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTitle.setText(task.getTitle());
    }
    @Override
    public int getItemCount() { return tasks.size(); }
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_task_title);
        }
    }
}
