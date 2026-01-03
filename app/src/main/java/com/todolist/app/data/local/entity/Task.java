package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Task Entity - Bảng chính của ứng dụng
 * Đại diện cho một công việc cần làm
 * <p>
 * Foreign Keys:
 * - project_id → projects.project_id (ON DELETE SET NULL)
 * - category_id → categories.category_id (ON DELETE SET NULL)
 */
@Entity(
        tableName = "tasks",
        foreignKeys = {
                @ForeignKey(
                        entity = Project.class,
                        parentColumns = "project_id",
                        childColumns = "project_id",
                        onDelete = ForeignKey.SET_NULL
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "category_id",
                        childColumns = "category_id",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index(value = "project_id"),
                @Index(value = "category_id"),
                @Index(value = "due_date"),
                @Index(value = "is_completed"),
                @Index(value = "priority")
        }
)
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private long taskId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "project_id")
    private Long projectId;  // Nullable - task có thể không thuộc project

    @ColumnInfo(name = "category_id")
    private Long categoryId;  // Nullable

    @ColumnInfo(name = "priority")
    private String priority;  // HIGH, MEDIUM, LOW

    @ColumnInfo(name = "status")
    private String status;  // PENDING, IN_PROGRESS, COMPLETED

    @ColumnInfo(name = "due_date")
    private Date dueDate;  // Nullable

    @ColumnInfo(name = "reminder_time")
    private Date reminderTime;  // Nullable

    @ColumnInfo(name = "estimated_duration")
    private Integer estimatedDuration;  // Phút, nullable

    @ColumnInfo(name = "actual_duration")
    private Integer actualDuration;  // Phút, nullable

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "completed_at")
    private Date completedAt;  // Nullable

    @ColumnInfo(name = "is_recurring")
    private boolean isRecurring;

    @ColumnInfo(name = "recurrence_rule")
    private String recurrenceRule;  // RFC 5545 format, nullable

    @ColumnInfo(name = "parent_recurring_task_id")
    private Long parentRecurringTaskId;  // Self-reference, nullable

    @ColumnInfo(name = "position")
    private int position;  // Để sắp xếp, drag-drop

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Task() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.priority = "MEDIUM";
        this.status = "PENDING";
        this.isCompleted = false;
        this.isRecurring = false;
        this.position = 0;
    }

    // ============================================
    // GETTERS
    // ============================================

    public long getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public Integer getActualDuration() {
        return actualDuration;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public Long getParentRecurringTaskId() {
        return parentRecurringTaskId;
    }

    public int getPosition() {
        return position;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    // ============================================
    // SETTERS
    // ============================================

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public void setActualDuration(Integer actualDuration) {
        this.actualDuration = actualDuration;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public void setParentRecurringTaskId(Long parentRecurringTaskId) {
        this.parentRecurringTaskId = parentRecurringTaskId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ============================================
    // UTILITY METHODS
    // ============================================

    /**
     * Check if task is overdue
     */
    public boolean isOverdue() {
        if (dueDate == null || isCompleted) {
            return false;
        }
        return dueDate.before(new Date());
    }

    /**
     * Get days until due date (negative if overdue)
     */
    public int getDaysUntilDue() {
        if (dueDate == null) {
            return Integer.MAX_VALUE;
        }
        long diff = dueDate.getTime() - new Date().getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}