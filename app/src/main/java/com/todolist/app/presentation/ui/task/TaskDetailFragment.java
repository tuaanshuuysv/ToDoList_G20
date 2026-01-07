package com.todolist.app.presentation.ui.task;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.app.R;
import com.todolist.app.data.local.database.TodoDatabase;
import com.todolist.app.data.local.entity.Subtask;
import com.todolist.app.data.local.entity.Task;
import com.todolist.app.data.repository.TaskRepository;
import com.todolist.app.presentation.adapter.SubTaskAdapter;
import com.todolist.app.presentation.util.AlarmUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment hiển thị chi tiết một công việc, quản lý Subtasks và Nhắc nhở.
 */
public class TaskDetailFragment extends Fragment {

    // 1. Khai báo biến thành phần và dữ liệu
    private long taskId;
    private TaskViewModel viewModel;
    private SubTaskAdapter subTaskAdapter;
    private Task currentTask;

    private EditText etSubtaskTitle;
    private TextView tvDetailReminder;
    private LinearLayout layoutSetReminder;
    private Calendar reminderCalendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getLong("TASK_ID");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews(view);
        observeData();
    }

    // --- 2. KHỞI TẠO HỆ THỐNG ---

    private void initViewModel() {
        TodoDatabase db = TodoDatabase.getInstance(requireContext());
        TaskRepository repo = new TaskRepository(db.taskDao(), db.subtaskDao());
        viewModel = new TaskViewModel(repo);
    }

    private void initViews(View view) {
        // Ánh xạ các View cơ bản
        etSubtaskTitle = view.findViewById(R.id.et_add_subtask_title);
        ImageButton btnAddSubtask = view.findViewById(R.id.btn_add_subtask);
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        RecyclerView rvSubtasks = view.findViewById(R.id.rv_subtasks);

        // Ánh xạ thành phần Nhắc nhở
        tvDetailReminder = view.findViewById(R.id.tv_detail_reminder);
        layoutSetReminder = view.findViewById(R.id.layout_set_reminder);

        // Thiết lập nút quay lại
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // Thiết lập sự kiện đặt nhắc nhở
        if (layoutSetReminder != null) {
            layoutSetReminder.setOnClickListener(v -> showTimePicker());
        }

        // Cấu hình RecyclerView cho Subtasks
        subTaskAdapter = new SubTaskAdapter();
        rvSubtasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSubtasks.setAdapter(subTaskAdapter);

        // Thiết lập sự kiện cho Subtask
        subTaskAdapter.setOnSubTaskClickListener(new SubTaskAdapter.OnSubTaskClickListener() {
            @Override
            public void onSubTaskChecked(Subtask subtask, boolean isChecked) {
                subtask.setCompleted(isChecked);
                viewModel.updateSubtask(subtask);
            }

            @Override
            public void onSubTaskDelete(Subtask subtask) {
                showDeleteDialog(subtask);
            }
        });

        // Nút thêm nhanh Subtask
        btnAddSubtask.setOnClickListener(v -> {
            String title = etSubtaskTitle.getText().toString().trim();
            if (!title.isEmpty()) {
                Subtask newSub = new Subtask(taskId, title);
                viewModel.insertSubtask(newSub);
                etSubtaskTitle.setText("");
            }
        });

        setupSwipeToDelete(rvSubtasks);
    }

    // --- 3. XỬ LÝ NHẮC NHỞ (REMINDER) ---

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute) -> {
                    reminderCalendar = Calendar.getInstance();
                    reminderCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    reminderCalendar.set(Calendar.MINUTE, minute);
                    reminderCalendar.set(Calendar.SECOND, 0);

                    // Nếu thời gian chọn đã trôi qua trong hôm nay, tự động dời sang ngày mai
                    if (reminderCalendar.getTimeInMillis() <= System.currentTimeMillis()) {
                        reminderCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    // Hiển thị giờ đã chọn lên giao diện
                    String timeStr = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    tvDetailReminder.setText("Nhắc nhở: " + timeStr);

                    if (currentTask != null) {
                        // 1. Lên lịch báo thức hệ thống
                        AlarmUtils.setAlarm(requireContext(),
                                reminderCalendar.getTimeInMillis(),
                                currentTask.getTitle(),
                                (int) currentTask.getTaskId());

                        // 2. Lưu thông tin vào Database
                        currentTask.setReminderTime(new Date(reminderCalendar.getTimeInMillis()));
                        viewModel.update(currentTask);

                        Toast.makeText(getContext(), "Đã hẹn giờ nhắc nhở!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Lỗi: Không tìm thấy dữ liệu công việc", Toast.LENGTH_SHORT).show();
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    // --- 4. THEO DÕI DỮ LIỆU (OBSERVATION) ---

    private void observeData() {
        // Quan sát thông tin Task chính để hiển thị Header
        viewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), task -> {
            if (task != null && getView() != null) {
                this.currentTask = task;
                updateHeaderUI(task);
            }
        });

        // Quan sát danh sách Subtasks
        viewModel.getSubtasks(taskId).observe(getViewLifecycleOwner(), subtasks -> {
            if (subtasks != null) subTaskAdapter.setSubtasks(subtasks);
        });
    }

    private void updateHeaderUI(Task task) {
        TextView tvTitle = getView().findViewById(R.id.tv_detail_task_title);
        TextView tvPriority = getView().findViewById(R.id.tv_detail_priority);
        TextView tvCategory = getView().findViewById(R.id.tv_detail_category);
        TextView tvDeadline = getView().findViewById(R.id.tv_detail_deadline);

        tvTitle.setText(task.getTitle());

        // Hiển thị Độ ưu tiên
        String priority = task.getPriority() != null ? task.getPriority() : "LOW";
        tvPriority.setText("Ưu tiên: " + priority);
        if (priority.equalsIgnoreCase("HIGH")) tvPriority.setTextColor(Color.RED);
        else if (priority.equalsIgnoreCase("MEDIUM")) tvPriority.setTextColor(Color.parseColor("#FFA500"));
        else tvPriority.setTextColor(Color.GREEN);

        // Hiển thị Phân loại
        long catId = task.getCategoryId();
        String catName; int catColor;
        if (catId == 1) { catName = "Cá nhân"; catColor = Color.parseColor("#008CFF"); }
        else if (catId == 2) { catName = "Công việc"; catColor = Color.parseColor("#FFBE00"); }
        else if (catId == 3) { catName = "Học tập"; catColor = Color.parseColor("#FF0018"); }
        else if (catId == 4) { catName = "Mua sắm"; catColor = Color.parseColor("#049E0B"); }
        else { catName = "Khác"; catColor = Color.GRAY; }

        tvCategory.setText(catName);
        tvCategory.setTextColor(catColor);

        // Hiển thị Hạn chót
        if (task.getDueDate() != null) {
            tvDeadline.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            tvDeadline.setText("📅 " + sdf.format(task.getDueDate()));

            if (!task.isCompleted() && task.getDueDate().before(new Date())) {
                tvDeadline.setTextColor(Color.RED);
                tvDeadline.setTypeface(null, Typeface.BOLD);
            } else {
                tvDeadline.setTextColor(Color.GRAY);
                tvDeadline.setTypeface(null, Typeface.NORMAL);
            }
        } else {
            tvDeadline.setVisibility(View.GONE);
        }

        // Hiển thị Nhắc nhở
        if (task.getReminderTime() != null) {
            SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            tvDetailReminder.setText("Nhắc nhở: " + timeSdf.format(task.getReminderTime()));
        } else {
            tvDetailReminder.setText("Đặt nhắc nhở");
        }
    }

    // --- 5. TIỆN ÍCH HỖ TRỢ (HELPERS) ---

    private void setupSwipeToDelete(RecyclerView rv) {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) { return false; }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Subtask subtask = subTaskAdapter.getSubtasks().get(position);
                showDeleteDialog(subtask);
                subTaskAdapter.notifyItemChanged(position);
            }
        };
        new ItemTouchHelper(callback).attachToRecyclerView(rv);
    }

    private void showDeleteDialog(Subtask subtask) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có muốn xóa bước '" + subtask.getTitle() + "'?")
                .setPositiveButton("Xóa", (d, w) -> viewModel.deleteSubtask(subtask))
                .setNegativeButton("Hủy", null)
                .show();
    }
}