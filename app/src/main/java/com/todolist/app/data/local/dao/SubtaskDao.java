package com.todolist.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.todolist.app.data.local.entity.Subtask;

import java.util.List;

@Dao
public interface SubtaskDao {

    // Lấy danh sách subtask theo task_id, sắp xếp theo vị trí (position)
    @Query("SELECT * FROM subtasks WHERE task_id = :taskId ORDER BY position ASC")
    LiveData<List<Subtask>> getSubtasksByTaskId(long taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSubtask(Subtask subtask);

    @Update
    void updateSubtask(Subtask subtask);

    @Delete
    void deleteSubtask(Subtask subtask);

    // Xóa tất cả subtask của một task (dùng khi xóa task cha)
    @Query("DELETE FROM subtasks WHERE task_id = :taskId")
    void deleteSubtasksByTaskId(long taskId);

    // Cập nhật trạng thái hoàn thành nhanh
    @Query("UPDATE subtasks SET is_completed = :isCompleted WHERE subtask_id = :subtaskId")
    void updateSubtaskStatus(long subtaskId, boolean isCompleted);
}