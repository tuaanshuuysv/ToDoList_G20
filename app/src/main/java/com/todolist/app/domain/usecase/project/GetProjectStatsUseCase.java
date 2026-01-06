package com.todolist.app.domain.usecase.project;

import androidx.lifecycle.LiveData;
import com.todolist.app.data.local.entity.relations.ProjectWithStats;
import com.todolist.app.data.repository.ProjectRepository;

public class GetProjectStatsUseCase {
    private final ProjectRepository repository;
    public GetProjectStatsUseCase(ProjectRepository repository) {
        this.repository = repository;
    }
    public LiveData<ProjectWithStats> execute(long projectId) {
        return repository.getProjectStats(projectId);
    }
}
