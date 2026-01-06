package com.todolist.app.domain.usecase.project;

import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.repository.ProjectRepository;

public class ArchiveProjectUseCase {
    private final ProjectRepository repository;
    public ArchiveProjectUseCase(ProjectRepository repository) {
        this.repository = repository;
    }
    public void execute(Project project) {
        project.setArchived(true);
        project.setUpdatedAt(new java.util.Date());
        repository.update(project);
    }
}
