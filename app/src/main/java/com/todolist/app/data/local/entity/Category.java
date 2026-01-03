package com.todolist.app.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Category Entity
 * Phân loại tasks theo lĩnh vực (Work, Study, Personal, etc.)
 */
@Entity(
        tableName = "categories",
        indices = {
                @Index(value = "name", unique = true)  // Tên category phải unique
        }
)
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private long categoryId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "color")
    private String color;  // Hex color: #RRGGBB

    @ColumnInfo(name = "icon")
    private String icon;  // Material icon name, nullable

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public Category() {
        this.createdAt = new Date();
        this.color = "#4CAF50";  // Default green
    }

    /**
     * Constructor với name và color
     */
    public Category(String name, String color) {
        this();
        this.name = name;
        this.color = color;
    }

    /**
     * Constructor đầy đủ
     */
    public Category(String name, String color, String icon) {
        this(name, color);
        this.icon = icon;
    }

    // ============================================
    // GETTERS
    // ============================================

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // ============================================
    // SETTERS
    // ============================================

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}