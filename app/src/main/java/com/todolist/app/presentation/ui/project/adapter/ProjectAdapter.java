package com.todolist.app.presentation.ui.project.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.app.R;
import com.todolist.app.data.local.entity.Project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private List<Project> projects = new ArrayList<>();
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Project project);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Lưu ý: Bạn cần tạo layout item_project.xml trước
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projects.get(position);
        holder.bind(project);
    }
    @Override
    public int getItemCount() {
        return projects.size();
    }
    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDescription;
        private View viewColorIndicator;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_project_name);
            tvDescription = itemView.findViewById(R.id.tv_project_description);
            viewColorIndicator = itemView.findViewById(R.id.view_project_color);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(projects.get(position));
                }
            });
        }
        public void bind(Project project) {
            tvName.setText(project.getName());
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(project);
                }
            });
        }
    }
}
