package com.todolist.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.local.entity.relations.ProjectWithStats;
import com.todolist.app.data.local.entity.relations.ProjectWithTasks;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProject(Project project);
    @Update
    void updateProject(Project project);
    @Delete
    void deleteProject(Project project);
    @Query("SELECT * FROM projects WHERE is_archived = 0 ORDER BY created_at DESC")
    LiveData<List<Project>> getAllActiveProjects();
    @Transaction
    @Query("SELECT * FROM projects WHERE project_id = :projectId")
    LiveData<ProjectWithTasks> getProjectWithTasks(long projectId);
    @Query("SELECT " +
            "p.*, " +
            "(SELECT COUNT(*) FROM tasks t WHERE t.project_id = p.project_id) as totalTasks, " +
            "(SELECT COUNT(*) FROM tasks t WHERE t.project_id = p.project_id AND t.is_completed = 1) as completedTasks, " +
            "(SELECT COUNT(*) FROM tasks t WHERE t.project_id = p.project_id AND t.is_completed = 0) as pendingTasks " +
            "FROM projects p WHERE p.project_id = :projectId")
    LiveData<ProjectWithStats> getProjectStats(long projectId);
    @Query("SELECT COUNT(*) FROM projects WHERE name = :name")
    int checkProjectNameExists(String name);
}
