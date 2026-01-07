package com.todolist.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.todolist.app.data.local.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    // 1. Thêm công việc mới
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(Task task);

    // 2. Cập nhật thông tin công việc
    @Update
    void updateTask(Task task);

    // 3. Xóa công việc
    @Delete
    void deleteTask(Task task);

    // 4. Lấy tất cả công việc (Sắp xếp theo vị trí và độ ưu tiên)
    @Query("SELECT * FROM tasks ORDER BY position ASC, priority DESC")
    LiveData<List<Task>> getAllTasks();

    // 5. Lấy chi tiết một công việc theo ID
    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    LiveData<Task> getTaskById(long taskId);

    // 6. Cập nhật nhanh trạng thái hoàn thành (Dùng cho nút checkbox)
    @Query("UPDATE tasks SET is_completed = :completed, completed_at = :completedAt WHERE task_id = :taskId")
    void updateCompletionStatus(long taskId, boolean completed, java.util.Date completedAt);

    // 7. Tìm kiếm công việc theo tên (Dùng cho module Search)
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :searchQuery || '%'")
    LiveData<List<Task>> searchTasks(String searchQuery);

    // 8. Lấy các công việc theo dự án (Dùng cho module Project)
    @Query("SELECT * FROM tasks WHERE project_id = :projectId")
    LiveData<List<Task>> getTasksByProject(long projectId);
}