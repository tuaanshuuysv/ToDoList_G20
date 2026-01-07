package com.todolist.app.presentation.ui.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.todolist.app.data.local.entity.Task;
import com.todolist.app.data.local.entity.Subtask; // Thêm import này
import com.todolist.app.data.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    // Nguồn dữ liệu gốc từ DB
    private final LiveData<List<Task>> sourceTasks;

    // Hai biến điều khiển (Filter & Sort)
    private final MutableLiveData<Integer> sortOrder = new MutableLiveData<>(1); // 1: Deadline, 2: Priority
    private final MutableLiveData<Long> filterCategoryId = new MutableLiveData<>(0L); // 0: Tất cả

    // LiveData cuối cùng để UI quan sát
    private final MediatorLiveData<List<Task>> tasks = new MediatorLiveData<>();

    public TaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.sourceTasks = taskRepository.getAllTasks();

        // Kết hợp 3 nguồn: Khi DB thay đổi, hoặc đổi Sort, hoặc đổi Filter -> Chạy hàm xử lý
        tasks.addSource(sourceTasks, value -> combineAndProcess());
        tasks.addSource(sortOrder, value -> combineAndProcess());
        tasks.addSource(filterCategoryId, value -> combineAndProcess());
    }

    /**
     * Logic chính: Lọc trước rồi mới Sắp xếp
     */
    private void combineAndProcess() {
        List<Task> originalList = sourceTasks.getValue();
        if (originalList == null) return;

        Integer order = sortOrder.getValue();
        Long categoryId = filterCategoryId.getValue();

        List<Task> processedList = new ArrayList<>();

        // 1. LỌC (Filtering)
        for (Task task : originalList) {
            if (categoryId == 0 || task.getCategoryId() == categoryId) {
                processedList.add(task);
            }
        }

        // 2. SẮP XẾP (Sorting)
        Collections.sort(processedList, (t1, t2) -> {
            // Luôn đẩy việc xong xuống dưới
            int completedCompare = Boolean.compare(t1.isCompleted(), t2.isCompleted());
            if (completedCompare != 0) return completedCompare;

            if (order != null && order == 1) {
                // Deadline trước -> Ưu tiên sau
                int res = compareByDeadline(t1, t2);
                if (res != 0) return res;
                return Integer.compare(getPriorityValue(t1.getPriority()), getPriorityValue(t2.getPriority()));
            } else {
                // Ưu tiên trước -> Deadline sau
                int res = Integer.compare(getPriorityValue(t1.getPriority()), getPriorityValue(t2.getPriority()));
                if (res != 0) return res;
                return compareByDeadline(t1, t2);
            }
        });

        tasks.setValue(processedList);
    }

    // --- HÀM HỖ TRỢ ---

    private int compareByDeadline(Task t1, Task t2) {
        if (t1.getDueDate() != null && t2.getDueDate() != null) {
            return t1.getDueDate().compareTo(t2.getDueDate());
        } else if (t1.getDueDate() != null) return -1;
        else if (t2.getDueDate() != null) return 1;
        return 0;
    }

    private int getPriorityValue(String priority) {
        if (priority == null) return 4;
        switch (priority.toUpperCase()) {
            case "HIGH": return 1;
            case "MEDIUM": return 2;
            case "LOW": return 3;
            default: return 4;
        }
    }

    // --- CÁC HÀM GỌI TỪ UI (TASK) ---

    public void setSortOrder(int order) { sortOrder.setValue(order); }

    public void setFilterCategory(long categoryId) { filterCategoryId.setValue(categoryId); }

    public LiveData<List<Task>> getTasks() { return tasks; }

    // Thêm vào TaskViewModel.java
    public LiveData<Task> getTaskById(long id) {
        return taskRepository.getTaskById(id);
    }

    public void insert(Task task) { executeInBackground(() -> taskRepository.insertTask(task)); }
    public void update(Task task) { executeInBackground(() -> taskRepository.updateTask(task)); }
    public void delete(Task task) { executeInBackground(() -> taskRepository.deleteTask(task)); }

    // --- CÁC HÀM GỌI TỪ UI (SUBTASK) - MỚI THÊM ---

    /**
     * Lấy danh sách Subtasks của một Task cụ thể
     */
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

    // --- CHẠY NGẦM ---

    private void executeInBackground(Runnable action) {
        new Thread(action).start();
    }
}