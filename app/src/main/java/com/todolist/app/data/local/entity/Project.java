package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Project Entity
 * Đại diện cho một dự án chứa nhiều tasks
 */
@Entity(
        tableName = "projects",
        foreignKeys = {
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "category_id",
                        childColumns = "default_category_id",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index(value = "is_archived"),
                @Index(value = "deadline"),
                @Index(value = "default_category_id")
        }
)
public class Project {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    private long projectId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "color")
    private String color;  // Hex color:  #RRGGBB

    @ColumnInfo(name = "icon")
    private String icon;  // Material icon name, nullable

    @ColumnInfo(name = "deadline")
    private Date deadline;  // Nullable

    @ColumnInfo(name = "default_category_id")
    private Long defaultCategoryId;  // Nullable

    @ColumnInfo(name = "view_type")
    private String viewType;  // LIST, KANBAN, TIMELINE, CALENDAR

    @ColumnInfo(name = "is_archived")
    private boolean isArchived;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Project() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.color = "#2196F3";  // Default blue
        this.viewType = "LIST";
        this.isArchived = false;
    }

    // ============================================
    // GETTERS
    // ============================================

    public long getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Long getDefaultCategoryId() {
        return defaultCategoryId;
    }

    public String getViewType() {
        return viewType;
    }

    public boolean isArchived() {
        return isArchived;
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

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDefaultCategoryId(Long defaultCategoryId) {
        this.defaultCategoryId = defaultCategoryId;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
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
     * Check if project is overdue
     */
    public boolean isOverdue() {
        if (deadline == null || isArchived) {
            return false;
        }
        return deadline.before(new Date());
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isArchived=" + isArchived +
                '}';
    }
}