package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Task Entity - Định nghĩa bảng "tasks" trong cơ sở dữ liệu.
 * Kết nối với bảng Project và Category thông qua Foreign Keys.
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

    // 1. CÁC TRƯỜNG DỮ LIỆU (COLUMNS)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private long taskId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "project_id")
    private Long projectId;  // Nullable - Task có thể không thuộc dự án nào

    @ColumnInfo(name = "category_id")
    private Long categoryId;

    @ColumnInfo(name = "priority")
    private String priority;  // HIGH, MEDIUM, LOW

    @ColumnInfo(name = "status")
    private String status;    // PENDING, IN_PROGRESS, COMPLETED

    @ColumnInfo(name = "due_date")
    private Date dueDate;

    @ColumnInfo(name = "reminder_time")
    private Date reminderTime;

    @ColumnInfo(name = "estimated_duration")
    private Integer estimatedDuration; // Thời gian ước tính (phút)

    @ColumnInfo(name = "actual_duration")
    private Integer actualDuration;    // Thời gian thực tế (phút)

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "completed_at")
    private Date completedAt;

    @ColumnInfo(name = "is_recurring")
    private boolean isRecurring; // Công việc lặp lại

    @ColumnInfo(name = "recurrence_rule")
    private String recurrenceRule;

    @ColumnInfo(name = "parent_recurring_task_id")
    private Long parentRecurringTaskId;

    @ColumnInfo(name = "position")
    private int position; // Hỗ trợ sắp xếp thủ công (drag-drop)

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    // ============================================
    // 2. CONSTRUCTORS
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

    public Task(String title, String description, boolean isCompleted) {
        this(); // Gọi constructor mặc định để khởi tạo các giá trị ban đầu
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.status = isCompleted ? "COMPLETED" : "PENDING";
    }

    // ============================================
    // 3. GETTERS & SETTERS
    // ============================================

    public long getTaskId() { return taskId; }
    public void setTaskId(long taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Date getReminderTime() { return reminderTime; }
    public void setReminderTime(Date reminderTime) { this.reminderTime = reminderTime; }

    public Integer getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(Integer estimatedDuration) { this.estimatedDuration = estimatedDuration; }

    public Integer getActualDuration() { return actualDuration; }
    public void setActualDuration(Integer actualDuration) { this.actualDuration = actualDuration; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public Date getCompletedAt() { return completedAt; }
    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }

    public boolean isRecurring() { return isRecurring; }
    public void setRecurring(boolean recurring) { isRecurring = recurring; }

    public String getRecurrenceRule() { return recurrenceRule; }
    public void setRecurrenceRule(String recurrenceRule) { this.recurrenceRule = recurrenceRule; }

    public Long getParentRecurringTaskId() { return parentRecurringTaskId; }
    public void setParentRecurringTaskId(Long parentRecurringTaskId) { this.parentRecurringTaskId = parentRecurringTaskId; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    // ============================================
    // 4. UTILITY METHODS (HÀM TIỆN ÍCH)
    // ============================================

    /**
     * Kiểm tra xem công việc có bị quá hạn không
     */
    public boolean isOverdue() {
        if (dueDate == null || isCompleted) return false;
        return dueDate.before(new Date());
    }

    /**
     * Lấy số ngày còn lại đến hạn (số âm nếu đã quá hạn)
     */
    public int getDaysUntilDue() {
        if (dueDate == null) return Integer.MAX_VALUE;
        long diff = dueDate.getTime() - new Date().getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}