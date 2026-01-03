package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * ActivityLog Entity
 * Ghi nhận lịch sử thay đổi (audit trail)
 */
@Entity(
        tableName = "activity_logs",
        indices = {
                @Index(value = {"entity_type", "entity_id"}),
                @Index(value = "created_at")
        }
)
public class ActivityLog {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    private long logId;

    @ColumnInfo(name = "entity_type")
    private String entityType;  // TASK, PROJECT, CATEGORY, MILESTONE

    @ColumnInfo(name = "entity_id")
    private long entityId;

    @ColumnInfo(name = "action")
    private String action;  // CREATED, UPDATED, DELETED, COMPLETED, MOVED, ARCHIVED

    @ColumnInfo(name = "details")
    private String details;  // JSON string with change details

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public ActivityLog() {
        this.createdAt = new Date();
    }

    /**
     * Constructor đầy đủ
     */
    public ActivityLog(String entityType, long entityId, String action, String details) {
        this();
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.details = details;
    }

    // ============================================
    // GETTERS
    // ============================================

    public long getLogId() {
        return logId;
    }

    public String getEntityType() {
        return entityType;
    }

    public long getEntityId() {
        return entityId;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // ============================================
    // SETTERS
    // ============================================

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ActivityLog{" +
                "logId=" + logId +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", action='" + action + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}