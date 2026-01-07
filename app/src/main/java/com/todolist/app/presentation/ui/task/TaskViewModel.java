package com.todolist.app.presentation.ui.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.todolist.app.data.local.entity.Task;
import com.todolist.app.data.local.entity.Subtask;
import com.todolist.app.data.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ViewModel quản lý logic nghiệp vụ cho Task và Subtask.
 * Sử dụng MediatorLiveData để kết hợp Lọc (Filter) và Sắp xếp (Sort).
 */
public class TaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    // 1. Nguồn dữ liệu
    private final LiveData<List<Task>> sourceTasks; // Dữ liệu gốc từ Database
    private final MutableLiveData<Integer> sortOrder = new MutableLiveData<>(1); // 1: Deadline, 2: Priority
    private final MutableLiveData<Long> filterCategoryId = new MutableLiveData<>(0L); // 0: Tất cả

    // LiveData cuối cùng mà UI (Fragment) sẽ quan sát
    private final MediatorLiveData<List<Task>> tasks = new MediatorLiveData<>();

    public TaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.sourceTasks = taskRepository.getAllTasks();

        // Thiết lập MediatorLiveData quan sát đồng thời 3 nguồn:
        // Khi DB thay đổi, hoặc đổi kiểu Sắp xếp, hoặc đổi bộ Lọc -> Tự động xử lý lại danh sách
        tasks.addSource(sourceTasks, value -> combineAndProcess());
        tasks.addSource(sortOrder, value -> combineAndProcess());
        tasks.addSource(filterCategoryId, value -> combineAndProcess());
    }

    // ============================================
    // 2. LOGIC XỬ LÝ DỮ LIỆU (FILTER & SORT)
    // ============================================

    private void combineAndProcess() {
        List<Task> originalList = sourceTasks.getValue();
        if (originalList == null) return;

        Integer order = sortOrder.getValue();
        Long categoryId = filterCategoryId.getValue();
        List<Task> processedList = new ArrayList<>();

        // Bước 1: LỌC (Filtering) theo Danh mục
        for (Task task : originalList) {
            if (categoryId == 0 || task.getCategoryId() == categoryId) {
                processedList.add(task);
            }
        }

        // Bước 2: SẮP XẾP (Sorting)
        Collections.sort(processedList, (t1, t2) -> {
            // Quy tắc chung: Đẩy các công việc đã hoàn thành xuống cuối danh sách
            int completedCompare = Boolean.compare(t1.isCompleted(), t2.isCompleted());
            if (completedCompare != 0) return completedCompare;

            if (order != null && order == 1) {
                // Ưu tiên Sắp xếp theo Hạn chót (Deadline)
                int res = compareByDeadline(t1, t2);
                if (res != 0) return res;
                return Integer.compare(getPriorityValue(t1.getPriority()), getPriorityValue(t2.getPriority()));
            } else {
                // Ưu tiên Sắp xếp theo Mức độ quan trọng (Priority)
                int res = Integer.compare(getPriorityValue(t1.getPriority()), getPriorityValue(t2.getPriority()));
                if (res != 0) return res;
                return compareByDeadline(t1, t2);
            }
        });

        tasks.setValue(processedList);
    }

    // --- Hàm hỗ trợ so sánh thời gian ---
    private int compareByDeadline(Task t1, Task t2) {
        if (t1.getDueDate() != null && t2.getDueDate() != null) {
            return t1.getDueDate().compareTo(t2.getDueDate());
        } else if (t1.getDueDate() != null) return -1;
        else if (t2.getDueDate() != null) return 1;
        return 0;
    }

    // --- Hàm chuyển đổi mức độ ưu tiên sang số để so sánh ---
    private int getPriorityValue(String priority) {
        if (priority == null) return 4;
        switch (priority.toUpperCase()) {
            case "HIGH": return 1;
            case "MEDIUM": return 2;
            case "LOW": return 3;
            default: return 4;
        }
    }

    // ============================================
    // 3. CÁC PHƯƠNG THỨC CHO TASK (GỌI TỪ UI)
    // ============================================

    public LiveData<List<Task>> getTasks() { return tasks; }

    public LiveData<Task> getTaskById(long id) { return taskRepository.getTaskById(id); }

    public void setSortOrder(int order) { sortOrder.setValue(order); }

    public void setFilterCategory(long categoryId) { filterCategoryId.setValue(categoryId); }

    public void insert(Task task) { executeInBackground(() -> taskRepository.insertTask(task)); }

    public void update(Task task) { executeInBackground(() -> taskRepository.updateTask(task)); }

    public void delete(Task task) { executeInBackground(() -> taskRepository.deleteTask(task)); }

    // ============================================
    // 4. CÁC PHƯƠNG THỨC CHO SUBTASK (GỌI TỪ UI)
    // ============================================

    public LiveData<List<Subtask>> getSubtasks(long taskId) {
        return taskRepository.getSubtasksByTaskId(taskId);
    }

    public void insertSubtask(Subtask subtask) {
        executeInBackground(() -> taskRepository.insertSubtask(subtask));
    }

    public void updateSubtask(Subtask subtask) {
        executeInBackground(() -> taskRepository.updateSubtask(subtask));
    }

    public void deleteSubtask(Subtask subtask) {
        executeInBackground(() -> taskRepository.deleteSubtask(subtask));
    }

    public void updateSubtaskStatus(long subtaskId, boolean isCompleted) {
        executeInBackground(() -> taskRepository.updateSubtaskStatus(subtaskId, isCompleted));
    }

    // ============================================
    // 5. QUẢN LÝ LUỒNG (THREADING)
    // ============================================

    private void executeInBackground(Runnable action) {
        // Chạy các thao tác ghi DB trên luồng riêng để tránh treo UI
        new Thread(action).start();
    }
}