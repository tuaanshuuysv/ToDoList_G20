package com.todolist.app.presentation.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.todolist.app.R;
import com.todolist.app.data.local.entity.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // 1. Khai báo biến và định dạng ngày tháng
    private List<Task> taskList = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    // 2. Khai báo các Interface xử lý sự kiện tương tác
    public interface OnTaskStatusChangeListener { void onStatusChange(Task task, boolean isCompleted); }
    public interface OnTaskClickListener { void onTaskClick(Task task); }
    public interface OnTaskDeleteListener { void onDelete(Task task); }
    public interface OnTaskEditListener { void onEdit(Task task); }

    private OnTaskStatusChangeListener onTaskStatusChangeListener;
    private OnTaskClickListener onTaskClickListener;
    private OnTaskDeleteListener onTaskDeleteListener;
    private OnTaskEditListener onTaskEditListener;

    // 3. Các hàm thiết lập dữ liệu và Listener từ bên ngoài
    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    public List<Task> getTaskList() { return taskList; }

    public void setOnTaskStatusChangeListener(OnTaskStatusChangeListener l) { this.onTaskStatusChangeListener = l; }
    public void setOnTaskClickListener(OnTaskClickListener l) { this.onTaskClickListener = l; }
    public void setOnTaskDeleteListener(OnTaskDeleteListener l) { this.onTaskDeleteListener = l; }
    public void setOnTaskEditListener(OnTaskEditListener l) { this.onTaskEditListener = l; }

    // 4. Khởi tạo Giao diện Item (onCreateViewHolder)
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    // 5. Đổ dữ liệu và xử lý logic hiển thị (onBindViewHolder)
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // --- Làm mới trạng thái hiển thị của Item ---
        holder.itemView.setAlpha(1.0f);
        holder.tvDeadline.setAlpha(1.0f);

        // --- Xử lý Tiêu đề & Trạng thái Checkbox ---
        holder.tvTitle.setText(task.getTitle());
        holder.cbStatus.setChecked(task.isCompleted());
        if (task.isCompleted()) {
            // Nếu đã xong: Gạch ngang chữ và làm mờ item
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemView.setAlpha(0.6f);
        } else {
            // Nếu chưa xong: Xóa gạch ngang
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // --- Xử lý Mô tả ---
        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            holder.tvDesc.setText(task.getDescription());
            holder.tvDesc.setVisibility(View.VISIBLE);
        } else {
            holder.tvDesc.setVisibility(View.GONE);
        }

        // --- Xử lý Phân loại (Categories) ---
        String[] catNames = {"Cá nhân", "Công việc", "Học tập", "Mua sắm"};
        Long catId = task.getCategoryId();
        if (catId != null && catId >= 1 && catId <= 4) {
            holder.tvCategory.setVisibility(View.VISIBLE);
            holder.tvCategory.setText("📁 " + catNames[catId.intValue() - 1]);
            switch (catId.intValue()) {
                case 1: holder.tvCategory.setTextColor(Color.parseColor("#008CFF")); break; // Cá nhân
                case 2: holder.tvCategory.setTextColor(Color.parseColor("#FFBE00")); break; // Công việc
                case 3: holder.tvCategory.setTextColor(Color.parseColor("#FF0018")); break; // Học tập
                case 4: holder.tvCategory.setTextColor(Color.parseColor("#049E0B")); break; // Mua sắm
            }
        } else {
            holder.tvCategory.setVisibility(View.GONE);
        }

        // --- Xử lý Độ ưu tiên (Priority) ---
        String priority = (task.getPriority() != null) ? task.getPriority().toUpperCase() : "LOW";
        holder.tvPriority.setText(priority);
        switch (priority) {
            case "HIGH":
                holder.tvPriority.setTextColor(Color.parseColor("#D32F2F"));
                holder.tvPriority.setBackgroundColor(Color.parseColor("#FFEBEE"));
                break;
            case "MEDIUM":
                holder.tvPriority.setTextColor(Color.parseColor("#F57C00"));
                holder.tvPriority.setBackgroundColor(Color.parseColor("#FFF3E0"));
                break;
            default: // LOW
                holder.tvPriority.setTextColor(Color.parseColor("#388E3C"));
                holder.tvPriority.setBackgroundColor(Color.parseColor("#E8F5E9"));
                break;
        }

        // --- Xử lý Hạn chót (Deadline) ---
        if (task.getDueDate() != null) {
            holder.tvDeadline.setVisibility(View.VISIBLE);
            holder.tvDeadline.setText("📅 " + dateFormat.format(task.getDueDate()));
            // Nếu quá hạn mà chưa hoàn thành thì hiện chữ Đỏ in đậm
            if (!task.isCompleted() && task.getDueDate().before(new Date())) {
                holder.tvDeadline.setTextColor(Color.RED);
                holder.tvDeadline.setTypeface(null, Typeface.BOLD);
            } else {
                holder.tvDeadline.setTextColor(Color.GRAY);
                holder.tvDeadline.setTypeface(null, Typeface.NORMAL);
            }
        } else {
            holder.tvDeadline.setVisibility(View.GONE);
        }

        // --- Gán các Sự kiện tương tác ---

        // Thay đổi trạng thái Hoàn thành
        holder.cbStatus.setOnClickListener(v -> {
            task.setCompleted(holder.cbStatus.isChecked());
            if (onTaskStatusChangeListener != null) onTaskStatusChangeListener.onStatusChange(task, task.isCompleted());
            notifyItemChanged(position);
        });

        // Xóa Task kèm thông báo xác nhận
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có muốn xóa công việc '" + task.getTitle() + "'?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        if (onTaskDeleteListener != null) onTaskDeleteListener.onDelete(task);
                    })
                    .setNegativeButton("Hủy", null).show();
        });

        // Mở tùy chọn sửa Task (nút 3 chấm)
        holder.btnMore.setOnClickListener(v -> {
            if (onTaskEditListener != null) onTaskEditListener.onEdit(task);
        });

        // Click vào vùng bất kỳ để xem chi tiết hoặc Subtasks
        holder.itemView.setOnClickListener(v -> {
            if (onTaskClickListener != null) onTaskClickListener.onTaskClick(task);
        });
    }

    @Override
    public int getItemCount() { return taskList.size(); }

    // 6. Lớp nắm giữ các thành phần UI (ViewHolder)
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvPriority, tvDeadline, tvCategory;
        CheckBox cbStatus;
        ImageButton btnDelete, btnMore;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_task_title);
            cbStatus = itemView.findViewById(R.id.cb_task_status);
            tvDesc = itemView.findViewById(R.id.tv_task_description);
            tvPriority = itemView.findViewById(R.id.tv_task_priority);
            tvDeadline = itemView.findViewById(R.id.tv_task_deadline);
            tvCategory = itemView.findViewById(R.id.tv_task_category);
            btnDelete = itemView.findViewById(R.id.btn_delete_task);
            btnMore = itemView.findViewById(R.id.btn_more_options);
        }
    }
}