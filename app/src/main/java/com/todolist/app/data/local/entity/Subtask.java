package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Subtask Entity
 * Các bước nhỏ trong một task lớn (checklist)
 * <p>
 * Foreign Key: CASCADE delete - khi xóa task, subtasks tự động xóa
 */
@Entity(
        tableName = "subtasks",
        foreignKeys = {
                @ForeignKey(
                        entity = Task.class,
                        parentColumns = "task_id",
                        childColumns = "task_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "task_id"),
                @Index(value = {"task_id", "position"})
        }
)
public class Subtask {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subtask_id")
    private long subtaskId;

    @ColumnInfo(name = "task_id")
    private long taskId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "position")
    private int position;  // Để sắp xếp, drag-drop

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Subtask() {
        this.createdAt = new Date();
        this.isCompleted = false;
        this.position = 0;
    }

    /**
     * Constructor với task ID và title
     */
    public Subtask(long taskId, String title) {
        this();
        this.taskId = taskId;
        this.title = title;
    }

    // ============================================
    // GETTERS
    // ============================================

    public long getSubtaskId() {
        return subtaskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getPosition() {
        return position;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // ============================================
    // SETTERS
    // ============================================

    public void setSubtaskId(long subtaskId) {
        this.subtaskId = subtaskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "subtaskId=" + subtaskId +
                ", taskId=" + taskId +
                ", title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                ", position=" + position +
                '}';
    }
}