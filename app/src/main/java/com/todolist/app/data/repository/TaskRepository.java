package com.todolist.app.data.repository;

import androidx.lifecycle.LiveData;
import com.todolist.app.data.local.dao.TaskDao;
import com.todolist.app.data.local.dao.SubtaskDao;
import com.todolist.app.data.local.entity.Task;
import com.todolist.app.data.local.entity.Subtask;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository quản lý dữ liệu Task và Subtask.
 * Kết nối giữa Data Layer (DAO) và Domain/Presentation Layer.
 */
@Singleton
public class TaskRepository {

    private final TaskDao taskDao;
    private final SubtaskDao subtaskDao; // MỚI: Thêm SubtaskDao

    @Inject
    public TaskRepository(TaskDao taskDao, SubtaskDao subtaskDao) { // Cập nhật Constructor
        this.taskDao = taskDao;
        this.subtaskDao = subtaskDao;
    }

    // ============================================
    // 1. CÁC THAO TÁC VỚI TASK (GIỮ NGUYÊN)
    // ============================================

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public LiveData<Task> getTaskById(long taskId) {
        return taskDao.getTaskById(taskId);
    }

    public LiveData<List<Task>> searchTasks(String query) {
        return taskDao.searchTasks("%" + query + "%");
    }

    public void insertTask(Task task) {
        Date now = new Date();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        taskDao.insertTask(task);
    }

    public void updateTask(Task task) {
        task.setUpdatedAt(new Date());
        taskDao.updateTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }

    public void updateTaskStatus(long taskId, boolean isCompleted) {
        Date completedAt = isCompleted ? new Date() : null;
        taskDao.updateCompletionStatus(taskId, isCompleted, completedAt);
    }

    // ============================================
    // 2. CÁC THAO TÁC VỚI SUBTASK (MỚI THÊM)
    // ============================================

    /**
     * Lấy danh sách công việc con của một Task
     */
    public LiveData<List<Subtask>> getSubtasksByTaskId(long taskId) {
        return subtaskDao.getSubtasksByTaskId(taskId);
    }

    /**
     * Thêm mới một công việc con
     */
    public void insertSubtask(Subtask subtask) {
        subtask.setCreatedAt(new Date());
        subtaskDao.insertSubtask(subtask);
    }

    /**
     * Cập nhật thông tin công việc con (tên, trạng thái)
     */
    public void updateSubtask(Subtask subtask) {
        subtaskDao.updateSubtask(subtask);
    }

    /**
     * Xóa một công việc con
     */
    public void deleteSubtask(Subtask subtask) {
        subtaskDao.deleteSubtask(subtask);
    }

    /**
     * Cập nhật nhanh trạng thái hoàn thành của Subtask
     */
    public void updateSubtaskStatus(long subtaskId, boolean isCompleted) {
        subtaskDao.updateSubtaskStatus(subtaskId, isCompleted);
    }
}