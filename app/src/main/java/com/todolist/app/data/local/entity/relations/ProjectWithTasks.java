package com.todolist.app.data.local.entity.relations;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.local.entity.Task;
import java.util.List;

public class ProjectWithTasks {
    @Embedded
    public Project project;
    @Relation(parentColumn = "project_id", entityColumn = "project_id")
    public List<Task> tasks;
}
