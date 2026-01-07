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
 * Đóng vai trò là nguồn dữ liệu duy nhất cho ViewModel.
 */
@Singleton
public class TaskRepository {

    private final TaskDao taskDao;
    private final SubtaskDao subtaskDao;

    // 1. Khởi tạo với Dependency Injection
    @Inject
    public TaskRepository(TaskDao taskDao, SubtaskDao subtaskDao) {
        this.taskDao = taskDao;
        this.subtaskDao = subtaskDao;
    }

    // ============================================
    // 2. THAO TÁC VỚI CÔNG VIỆC CHÍNH (TASK)
    // ============================================

    /**
     * Lấy toàn bộ danh sách công việc
     */
    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    /**
     * Lấy chi tiết một công việc theo ID
     */
    public LiveData<Task> getTaskById(long taskId) {
        return taskDao.getTaskById(taskId);
    }

    /**
     * Tìm kiếm công việc theo từ khóa
     */
    public LiveData<List<Task>> searchTasks(String query) {
        return taskDao.searchTasks("%" + query + "%");
    }

    /**
     * Thêm mới công việc (Tự động gán ngày tạo)
     */
    public void insertTask(Task task) {
        Date now = new Date();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        taskDao.insertTask(task);
    }

    /**
     * Cập nhật thông tin công việc (Tự động gán ngày cập nhật)
     */
    public void updateTask(Task task) {
        task.setUpdatedAt(new Date());
        taskDao.updateTask(task);
    }

    /**
     * Xóa công việc
     */
    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }

    /**
     * Cập nhật nhanh trạng thái hoàn thành của Task
     */
    public void updateTaskStatus(long taskId, boolean isCompleted) {
        Date completedAt = isCompleted ? new Date() : null;
        taskDao.updateCompletionStatus(taskId, isCompleted, completedAt);
    }

    // ============================================
    // 3. THAO TÁC VỚI CÔNG VIỆC CON (SUBTASK)
    // ============================================

    /**
     * Lấy danh sách toàn bộ công việc con của một Task mẹ
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
     * Cập nhật thông tin chi tiết công việc con
     */
    public void updateSubtask(Subtask subtask) {
        subtaskDao.updateSubtask(subtask);
    }

    /**
     * Xóa công việc con
     */
    public void deleteSubtask(Subtask subtask) {
        subtaskDao.deleteSubtask(subtask);
    }

    /**
     * Cập nhật nhanh trạng thái hoàn thành (Check/Uncheck) của Subtask
     */
    public void updateSubtaskStatus(long subtaskId, boolean isCompleted) {
        subtaskDao.updateSubtaskStatus(subtaskId, isCompleted);
    }
}