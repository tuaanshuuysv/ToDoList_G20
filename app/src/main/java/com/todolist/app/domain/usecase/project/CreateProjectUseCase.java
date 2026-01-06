package com.todolist.app.domain.usecase.project;

import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.repository.ProjectRepository;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateProjectUseCase {
    private final ProjectRepository repository;
    private final ExecutorService executorService;
    public CreateProjectUseCase(ProjectRepository repository) {
        this.repository = repository;
        this.executorService = Executors.newSingleThreadExecutor();
    }
    public interface Callback {
        void onSuccess();
        void onError(String message);
    }
    public void execute(String name, String description, String color, String icon, Date deadline, Callback callback) {
        if (name == null || name.trim().isEmpty()) {
            callback.onError("Tên dự án không được để trống!");
            return;
        }
        executorService.execute(() -> {
            if (repository.isProjectNameExists(name)) {
                callback.onError("Tên dự án đã tồn tại!");
            }
            else {
                Project project = new Project();
                project.setName(name);
                project.setDescription(description);
                project.setColor(color != null ? color : "#2196F3");
                project.setIcon(icon);
                project.setDeadline(deadline);
                project.setViewType("LIST");
                project.setArchived(false);
                project.setCreatedAt(new Date());
                project.setUpdatedAt(new Date());
                repository.insert(project);
                callback.onSuccess();
            }
        });
    }
}
