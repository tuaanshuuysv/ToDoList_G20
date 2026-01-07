package com.todolist.app.presentation.ui.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.todolist.app.R;
import com.todolist.app.data.local.database.TodoDatabase;
import com.todolist.app.data.local.entity.Task;
import com.todolist.app.data.repository.TaskRepository;
import com.todolist.app.presentation.adapter.TaskAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaskListFragment extends Fragment {

    // 1. Khai báo biến
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    // 2. Lifecycle - Khởi tạo View
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCore();             // Khởi tạo ViewModel & Data
        initRecyclerView(view); // Thiết lập danh sách Task
        setupHeaderActions(view); // Thiết lập nút Sắp xếp (Sort)
        setupFilterActions(view); // Thiết lập bộ lọc danh mục (ChipGroup)
        setupFab(view);         // Thiết lập nút thêm mới (Floating Action Button)
    }

    // --- 3. CÁC HÀM KHỞI TẠO HỆ THỐNG ---

    private void initCore() {
        TodoDatabase db = TodoDatabase.getInstance(requireContext());
        TaskRepository repository = new TaskRepository(db.taskDao(), db.subtaskDao());
        taskViewModel = new TaskViewModel(repository);

        // Quan sát dữ liệu từ LiveData
        taskViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) taskAdapter.setTasks(tasks);
        });
    }

    private void initRecyclerView(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_tasks);
        taskAdapter = new TaskAdapter();

        // Sự kiện: Xóa Task
        taskAdapter.setOnTaskDeleteListener(task -> taskViewModel.delete(task));

        // Sự kiện: Bấm vào Item để xem chi tiết/Subtasks
        taskAdapter.setOnTaskClickListener(this::openTaskDetailFragment);

        // Sự kiện: Bấm vào nút 3 chấm để Sửa nhanh
        taskAdapter.setOnTaskEditListener(this::showTaskDialog);

        // Sự kiện: Thay đổi trạng thái Hoàn thành (Checkbox)
        taskAdapter.setOnTaskStatusChangeListener((task, isDone) -> {
            task.setCompleted(isDone);
            taskViewModel.update(task);
        });

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(taskAdapter);

        setupSwipeToDelete(rv); // Kích hoạt tính năng vuốt để xóa
    }

    // --- 4. CÁC HÀM THIẾT LẬP HÀNH ĐỘNG (INTERACTIONS) ---

    private void setupHeaderActions(View view) {
        View btnSort = view.findViewById(R.id.btn_sort);
        if (btnSort != null) {
            btnSort.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(requireContext(), v);
                popup.getMenu().add(0, 1, 0, "Ưu tiên: Deadline (Sớm nhất)");
                popup.getMenu().add(0, 2, 1, "Ưu tiên: Quan trọng (High)");

                popup.setOnMenuItemClickListener(item -> {
                    taskViewModel.setSortOrder(item.getItemId());
                    Toast.makeText(requireContext(), "Đã thay đổi thứ tự sắp xếp", Toast.LENGTH_SHORT).show();
                    return true;
                });
                popup.show();
            });
        }
    }

    private void setupFilterActions(View view) {
        ChipGroup chipGroup = view.findViewById(R.id.chip_group_filter);
        if (chipGroup != null) {
            chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int categoryId = 0; // Mặc định là 'Tất cả'
                if (checkedId == R.id.chip_personal) categoryId = 1;
                else if (checkedId == R.id.chip_work) categoryId = 2;
                else if (checkedId == R.id.chip_study) categoryId = 3;
                else if (checkedId == R.id.chip_shopping) categoryId = 4;

                taskViewModel.setFilterCategory(categoryId);
            });
        }
    }

    private void setupFab(View view) {
        view.findViewById(R.id.fab_add_task).setOnClickListener(v -> showTaskDialog(null));
    }

    // --- 5. DIALOG THÊM & SỬA CÔNG VIỆC ---

    private void showTaskDialog(@Nullable Task existingTask) {
        boolean isEdit = existingTask != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(isEdit ? "CHỈNH SỬA CÔNG VIỆC" : "THÊM CÔNG VIỆC MỚI");

        // Giao diện cuộn cho Dialog
        ScrollView scrollView = new ScrollView(requireContext());
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 40, 60, 40);

        // Tạo các ô nhập liệu
        final EditText etTitle = createInput("Tiêu đề", layout, isEdit ? existingTask.getTitle() : "", false);
        final EditText etDesc = createInput("Mô tả", layout, isEdit ? existingTask.getDescription() : "", false);
        final Spinner spCat = addSpinner("Danh mục", layout, new String[]{"Cá nhân", "Công việc", "Học tập", "Mua sắm"});
        final Spinner spPri = addSpinner("Mức độ ưu tiên", layout, new String[]{"LOW", "MEDIUM", "HIGH"});
        final EditText etDate = createInput("Hạn chót", layout, "", true);

        final Calendar cal = Calendar.getInstance();
        if (isEdit) {
            spCat.setSelection((int) (existingTask.getCategoryId() - 1));
            String p = existingTask.getPriority();
            spPri.setSelection("HIGH".equals(p) ? 2 : ("MEDIUM".equals(p) ? 1 : 0));

            if (existingTask.getDueDate() != null) {
                cal.setTime(existingTask.getDueDate());
                etDate.setText(dateTimeFormat.format(existingTask.getDueDate()));
            }
        }

        setupDateTimePicker(etDate, cal);
        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton(isEdit ? "CẬP NHẬT" : "THÊM NGAY", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập tiêu đề!", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = isEdit ? existingTask : new Task(title, etDesc.getText().toString().trim(), false);
            task.setTitle(title);
            task.setDescription(etDesc.getText().toString().trim());
            task.setCategoryId((long) spCat.getSelectedItemPosition() + 1);
            task.setPriority(spPri.getSelectedItem().toString());
            task.setDueDate(etDate.getText().toString().isEmpty() ? null : cal.getTime());

            if (isEdit) taskViewModel.update(task); else taskViewModel.insert(task);
        });

        builder.setNegativeButton("HỦY", null);
        builder.show();
    }

    // --- 6. CÁC HÀM HỖ TRỢ GIAO DIỆN (HELPERS) ---

    private void openTaskDetailFragment(Task task) {
        Bundle bundle = new Bundle();
        bundle.putLong("TASK_ID", task.getTaskId());

        TaskDetailFragment detailFragment = new TaskDetailFragment();
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(android.R.id.content, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupSwipeToDelete(RecyclerView rv) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView r, @NonNull RecyclerView.ViewHolder h, @NonNull RecyclerView.ViewHolder t) { return false; }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Task task = taskAdapter.getTaskList().get(position);
                new AlertDialog.Builder(requireContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có muốn xóa công việc '" + task.getTitle() + "'?")
                        .setPositiveButton("Xóa", (d, w) -> taskViewModel.delete(task))
                        .setNegativeButton("Hủy", (d, w) -> taskAdapter.notifyItemChanged(position))
                        .setCancelable(false).show();
            }
        }).attachToRecyclerView(rv);
    }

    private void setupDateTimePicker(EditText et, Calendar cal) {
        Runnable showPicker = () -> {
            new DatePickerDialog(requireContext(), (view, y, m, d) -> {
                cal.set(y, m, d);
                new TimePickerDialog(requireContext(), (tv, h, min) -> {
                    cal.set(Calendar.HOUR_OF_DAY, h);
                    cal.set(Calendar.MINUTE, min);
                    et.setText(dateTimeFormat.format(cal.getTime()));
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        };
        et.setOnClickListener(v -> showPicker.run());
        et.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) showPicker.run(); });
    }

    private EditText createInput(String label, LinearLayout container, String value, boolean isDatePicker) {
        addLabel(label, container);
        EditText et = new EditText(requireContext());
        et.setText(value);
        if (isDatePicker) {
            et.setHint("Chọn Ngày & Giờ");
            et.setFocusable(true);
            et.setFocusableInTouchMode(false);
        }
        container.addView(et);
        addSpacer(container);
        return et;
    }

    private Spinner addSpinner(String label, LinearLayout container, String[] items) {
        addLabel(label, container);
        Spinner s = new Spinner(requireContext());
        s.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items));
        container.addView(s);
        addSpacer(container);
        return s;
    }

    private void addLabel(String text, LinearLayout container) {
        TextView tv = new TextView(requireContext());
        tv.setText(text);
        tv.setTypeface(null, Typeface.BOLD);
        container.addView(tv);
    }

    private void addSpacer(LinearLayout layout) {
        layout.addView(new View(requireContext()), new LinearLayout.LayoutParams(1, 30));
    }
}