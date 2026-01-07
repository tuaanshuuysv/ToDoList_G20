package com.todolist.app.presentation.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.app.R;
import com.todolist.app.data.local.entity.Subtask;

import java.util.ArrayList;
import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {

    private List<Subtask> subtasks = new ArrayList<>();
    private OnSubTaskClickListener listener;

    // --- HÀM MỚI THÊM ĐỂ PHỤC VỤ SWIPE TO DELETE ---
    public List<Subtask> getSubtasks() {
        return subtasks;
    }
    // ----------------------------------------------

    public interface OnSubTaskClickListener {
        void onSubTaskChecked(Subtask subtask, boolean isChecked);
        void onSubTaskDelete(Subtask subtask);
    }

    public void setOnSubTaskClickListener(OnSubTaskClickListener listener) {
        this.listener = listener;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtask, parent, false);
        return new SubTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {
        Subtask subtask = subtasks.get(position);
        holder.bind(subtask);
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    class SubTaskViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView tvTitle;
        private ImageButton btnDelete;

        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_subtask);
            tvTitle = itemView.findViewById(R.id.tv_subtask_title);
            btnDelete = itemView.findViewById(R.id.btn_delete_subtask);
        }

        public void bind(Subtask subtask) {
            tvTitle.setText(subtask.getTitle());
            updateStroke(subtask.isCompleted());

            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(subtask.isCompleted());

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateStroke(isChecked);
                if (listener != null) {
                    listener.onSubTaskChecked(subtask, isChecked);
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSubTaskDelete(subtask);
                }
            });
        }

        private void updateStroke(boolean isCompleted) {
            if (isCompleted) {
                tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTitle.setAlpha(0.5f);
            } else {
                tvTitle.setPaintFlags(tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                tvTitle.setAlpha(1.0f);
            }
        }
    }
}