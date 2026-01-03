package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "task_tags",
        primaryKeys = {"task_id", "tag_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Task.class,
                        parentColumns = "task_id",
                        childColumns = "task_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Tag.class,
                        parentColumns = "tag_id",
                        childColumns = "tag_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "task_id"),
                @Index(value = "tag_id")
        }
)
public class TaskTag {

    @ColumnInfo(name = "task_id")
    private long taskId;

    @ColumnInfo(name = "tag_id")
    private long tagId;

    public TaskTag(long taskId, long tagId) {
        this.taskId = taskId;
        this.tagId = tagId;
    }

    // Getters & Setters
    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}