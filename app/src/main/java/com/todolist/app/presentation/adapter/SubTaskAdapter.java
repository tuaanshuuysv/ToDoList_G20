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

/**
 * Adapter quản lý danh sách các công việc con (Subtasks).
 * Hỗ trợ đánh dấu hoàn thành và xóa từng hạng mục.
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {

    // 1. Biến lưu trữ dữ liệu và Interface sự kiện
    private List<Subtask> subtasks = new ArrayList<>();
    private OnSubTaskClickListener listener;

    public interface OnSubTaskClickListener {
        void onSubTaskChecked(Subtask subtask, boolean isChecked);
        void onSubTaskDelete(Subtask subtask);
    }

    // 2. Các hàm cập nhật dữ liệu và thiết lập Listener
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
        notifyDataSetChanged();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setOnSubTaskClickListener(OnSubTaskClickListener listener) {
        this.listener = listener;
    }

    // 3. Khởi tạo Giao diện (onCreateViewHolder)
    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtask, parent, false);
        return new SubTaskViewHolder(view);
    }

    // 4. Đổ dữ liệu vào View (onBindViewHolder)
    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {
        Subtask subtask = subtasks.get(position);
        holder.bind(subtask);
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    // 5. Lớp nắm giữ các thành phần UI (ViewHolder)
    class SubTaskViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final TextView tvTitle;
        private final ImageButton btnDelete;

        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_subtask);
            tvTitle = itemView.findViewById(R.id.tv_subtask_title);
            btnDelete = itemView.findViewById(R.id.btn_delete_subtask);
        }

        public void bind(Subtask subtask) {
            tvTitle.setText(subtask.getTitle());

            // Xử lý trạng thái hiển thị (gạch ngang và độ mờ)
            updateUIState(subtask.isCompleted());

            // QUAN TRỌNG: Gỡ listener cũ trước khi setChecked để tránh chạy sai logic khi RecyclerView tái sử dụng View
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(subtask.isCompleted());

            // Lắng nghe sự kiện thay đổi Checkbox
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateUIState(isChecked);
                if (listener != null) {
                    listener.onSubTaskChecked(subtask, isChecked);
                }
            });

            // Lắng nghe sự kiện nút xóa
            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSubTaskDelete(subtask);
                }
            });
        }

        /**
         * Cập nhật hiệu ứng chữ gạch ngang và độ mờ khi hoàn thành
         */
        private void updateUIState(boolean isCompleted) {
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