package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Milestone Entity
 * Mốc quan trọng trong một project (Phase 1, Phase 2, etc.)
 * <p>
 * Foreign Key: CASCADE delete - khi xóa project, milestones tự động xóa
 */
@Entity(
        tableName = "milestones",
        foreignKeys = {
                @ForeignKey(
                        entity = Project.class,
                        parentColumns = "project_id",
                        childColumns = "project_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "project_id"),
                @Index(value = "due_date")
        }
)
public class Milestone {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "milestone_id")
    private long milestoneId;

    @ColumnInfo(name = "project_id")
    private long projectId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "due_date")
    private Date dueDate;  // Nullable

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Milestone() {
        this.createdAt = new Date();
        this.isCompleted = false;
    }

    /**
     * Constructor với project ID và name
     */
    public Milestone(long projectId, String name) {
        this();
        this.projectId = projectId;
        this.name = name;
    }

    // ============================================
    // GETTERS
    // ============================================

    public long getMilestoneId() {
        return milestoneId;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // ============================================
    // SETTERS
    // ============================================

    public void setMilestoneId(long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // ============================================
    // UTILITY METHODS
    // ============================================

    /**
     * Check if milestone is overdue
     */
    public boolean isOverdue() {
        if (dueDate == null || isCompleted) {
            return false;
        }
        return dueDate.before(new Date());
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "milestoneId=" + milestoneId +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}