package com.todolist.app.domain.usecase.project;

import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.repository.ProjectRepository;
import java.util.Date;

public class UpdateProjectUseCase {
    private final ProjectRepository repository;
    public UpdateProjectUseCase(ProjectRepository repository) {
        this.repository = repository;
    }
    public void execute(Project project) {
        project.setUpdatedAt(new Date());
        repository.update(project);
    }
}
