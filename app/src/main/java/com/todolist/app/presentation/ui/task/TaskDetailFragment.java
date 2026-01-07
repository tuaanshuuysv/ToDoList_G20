package com.todolist.app.presentation.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color; // Thêm để đổi màu chữ

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.app.R;
import com.todolist.app.data.local.database.TodoDatabase;
import com.todolist.app.data.local.entity.Subtask;
import com.todolist.app.data.repository.TaskRepository;
import com.todolist.app.presentation.adapter.SubTaskAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskDetailFragment extends Fragment {

    private long taskId;
    private TaskViewModel viewModel;
    private SubTaskAdapter subTaskAdapter;
    private EditText etSubtaskTitle;

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

    private void initViewModel() {
        TodoDatabase db = TodoDatabase.getInstance(requireContext());
        TaskRepository repo = new TaskRepository(db.taskDao(), db.subtaskDao());
        viewModel = new TaskViewModel(repo);
    }

    private void initViews(View view) {
        etSubtaskTitle = view.findViewById(R.id.et_add_subtask_title);
        ImageButton btnAddSubtask = view.findViewById(R.id.btn_add_subtask);
        RecyclerView rvSubtasks = view.findViewById(R.id.rv_subtasks);
        ImageButton btnBack = view.findViewById(R.id.btn_back);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        subTaskAdapter = new SubTaskAdapter();
        rvSubtasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSubtasks.setAdapter(subTaskAdapter);

        setupSwipeToDelete(rvSubtasks);

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

        btnAddSubtask.setOnClickListener(v -> {
            String title = etSubtaskTitle.getText().toString().trim();
            if (!title.isEmpty()) {
                Subtask newSub = new Subtask(taskId, title);
                viewModel.insertSubtask(newSub);
                etSubtaskTitle.setText("");
            }
        });
    }

    private void setupSwipeToDelete(RecyclerView rv) {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

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
        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có muốn xóa bước '" + subtask.getTitle() + "'?")
                .setPositiveButton("Xóa", (d, w) -> viewModel.deleteSubtask(subtask))
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void observeData() {
        // QUAN TRỌNG: Đổ dữ liệu chi tiết cho Header
        viewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), task -> {
            if (task != null && getView() != null) {
                TextView tvTitle = getView().findViewById(R.id.tv_detail_task_title);
                TextView tvPriority = getView().findViewById(R.id.tv_detail_priority);
                TextView tvCategory = getView().findViewById(R.id.tv_detail_category);
                TextView tvDeadline = getView().findViewById(R.id.tv_detail_deadline);

                // 1. Tên Task
                tvTitle.setText(task.getTitle());

                // 2. Độ ưu tiên & Đổi màu theo mức độ
                String priority = task.getPriority() != null ? task.getPriority() : "LOW";
                tvPriority.setText("Ưu tiên: " + priority);
                if (priority.equalsIgnoreCase("HIGH")) {
                    tvPriority.setTextColor(Color.RED);
                } else if (priority.equalsIgnoreCase("MEDIUM")) {
                    tvPriority.setTextColor(Color.parseColor("#FFA500")); // Màu cam
                } else {
                    tvPriority.setTextColor(Color.GREEN);
                }

                // 3. Phân loại (Long có thể sửa logic lấy tên category ở đây)
                long catId = task.getCategoryId();
                String catName;
                int catColor;

                if (catId == 1) {
                    catName = "Cá nhân";
                    catColor = Color.parseColor("#008CFF"); // Xanh dương nhạt
                } else if (catId == 2) {
                    catName = "Công việc";
                    catColor = Color.parseColor("#FFBE00"); // Xanh lá nhạt
                } else if (catId == 3) {
                    catName = "Học tập";
                    catColor = Color.parseColor("#FF0018"); // Cam nhạt
                } else if (catId == 4) {
                    catName = "Mua sắm";
                    catColor = Color.parseColor("#049E0B"); // Hồng nhạt (cho hợp đi mua sắm nè)
                } else {
                    catName = "Khác";
                    catColor = Color.parseColor("#EEEEEE"); // Xám nhạt
                }

                tvCategory.setText(catName);
                tvCategory.setTextColor(catColor);

                // 4. Hạn chót
                if (task.getDueDate() != null) {
                    tvDeadline.setVisibility(View.VISIBLE);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    tvDeadline.setText("📅 " + sdf.format(task.getDueDate()));

                    // Logic đồng bộ hoàn toàn với màn hình ngoài của Long
                    if (!task.isCompleted() && task.getDueDate().before(new java.util.Date())) {
                        // TRỄ HẠN: Màu đỏ và Chữ đậm
                        tvDeadline.setTextColor(Color.RED);
                        tvDeadline.setTypeface(null, android.graphics.Typeface.BOLD);
                    } else {
                        // CHƯA TRỄ hoặc ĐÃ XONG: Màu xám và Chữ bình thường
                        tvDeadline.setTextColor(Color.GRAY);
                        tvDeadline.setTypeface(null, android.graphics.Typeface.NORMAL);
                    }
                } else {
                    // Không có ngày thì ẩn đi hoặc hiện "Không có hạn" tùy Long
                    tvDeadline.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getSubtasks(taskId).observe(getViewLifecycleOwner(), subtasks -> {
            if (subtasks != null) subTaskAdapter.setSubtasks(subtasks);
        });
    }
}