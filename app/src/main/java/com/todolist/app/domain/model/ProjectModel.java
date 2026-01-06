package com.todolist.app.domain.model;

import java.util.Date;

public class ProjectModel {
    private long projectId;
    private String name;
    private String description;
    private String color;
    private String icon;
    private Date deadline;
    private Long defaultCategoryId;
    private String viewType;
    private boolean isArchived;
    public ProjectModel() {}
    public long getProjectId() {
        return projectId;
    }
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private int completedCount;
    private int totalCount;
    public void setStats(int completed, int total) {
        this.completedCount = completed;
        this.totalCount = total;
    }
    public int getProgress() {
        if (totalCount == 0) return 0;
        return (int) ((completedCount / (float) totalCount) * 100);
    }
}
