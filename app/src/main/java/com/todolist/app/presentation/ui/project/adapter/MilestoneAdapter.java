package com.todolist.app.presentation.ui.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.todolist.app.R;
import com.todolist.app.data.local.entity.Milestone;
import java.util.ArrayList;
import java.util.List;

public class MilestoneAdapter extends RecyclerView.Adapter<MilestoneAdapter.ViewHolder> {
    private List<Milestone> milestones = new ArrayList<>();
    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Milestone milestone = milestones.get(position);
        holder.tvName.setText(milestone.getTitle());
    }
    @Override
    public int getItemCount() { return milestones.size(); }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_project_name);
        }
    }
}
