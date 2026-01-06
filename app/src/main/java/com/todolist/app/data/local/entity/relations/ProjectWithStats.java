package com.todolist.app.data.local.entity.relations;

import androidx.room.Embedded;
import com.todolist.app.data.local.entity.Project;

public class ProjectWithStats {
    @Embedded
    public Project project;
    public int totalTasks;
    public int completedTasks;
    public int pendingTasks;
}
