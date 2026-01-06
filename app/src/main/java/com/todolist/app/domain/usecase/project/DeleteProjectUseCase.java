package com.todolist.app.domain.usecase.project;

import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.repository.ProjectRepository;

public class DeleteProjectUseCase {
    private final ProjectRepository repository;
    public DeleteProjectUseCase(ProjectRepository repository) {
        this.repository = repository;
    }
    public void execute(Project project) {
        repository.delete(project);
    }
}
