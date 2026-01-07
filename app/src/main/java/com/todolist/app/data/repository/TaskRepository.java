package com.todolist.app.data.repository;

import androidx.lifecycle.LiveData;
import com.todolist.app.data.local.dao.TaskDao;
import com.todolist.app.data.local.entity.Task;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository quản lý dữ liệu Task.
 * Kết nối giữa Data Layer (DAO) và Domain/Presentation Layer.
 */
@Singleton
public class TaskRepository {

    private final TaskDao taskDao;

    @Inject
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // --- 1. TRUY VẤN DỮ LIỆU (READ) ---

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public LiveData<Task> getTaskById(long taskId) {
        return taskDao.getTaskById(taskId);
    }

    public LiveData<List<Task>> searchTasks(String query) {
        return taskDao.searchTasks("%" + query + "%"); // Thêm % để search LIKE chuẩn SQL
    }

    // --- 2. THAO TÁC DỮ LIỆU (WRITE) ---

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

    /**
     * Cập nhật nhanh trạng thái hoàn thành công việc
     */
    public void updateTaskStatus(long taskId, boolean isCompleted) {
        Date completedAt = isCompleted ? new Date() : null;
        taskDao.updateCompletionStatus(taskId, isCompleted, completedAt);
    }
}