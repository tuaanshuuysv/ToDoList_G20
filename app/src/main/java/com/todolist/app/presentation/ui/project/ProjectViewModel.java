package com.todolist.app.presentation.ui.project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.local.entity.relations.ProjectWithStats;
import com.todolist.app.data.repository.ProjectRepository;
import com.todolist.app.domain.usecase.project.CreateProjectUseCase;
import com.todolist.app.domain.usecase.project.DeleteProjectUseCase;
import com.todolist.app.domain.usecase.project.GetProjectStatsUseCase;

import java.util.Date;
import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    private final ProjectRepository repository;
    private final CreateProjectUseCase createProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;
    private final GetProjectStatsUseCase getProjectStatsUseCase;
    private final LiveData<List<Project>> activeProjects;
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> createSuccess = new MutableLiveData<>();
    public ProjectViewModel(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository(application);
        createProjectUseCase = new CreateProjectUseCase(repository);
        deleteProjectUseCase = new DeleteProjectUseCase(repository);
        getProjectStatsUseCase = new GetProjectStatsUseCase(repository);

        activeProjects = repository.getAllActiveProjects();
    }
    public LiveData<List<Project>> getActiveProjects() {
        return activeProjects;
    }
    public LiveData<String> getMessage() {
        return message;
    }
    public LiveData<Boolean> getCreateSuccess() {
        return createSuccess;
    }
    public void createProject(String name, String description, String color, String icon, Date deadline) {
        createProjectUseCase.execute(name, description, color, icon, deadline, new CreateProjectUseCase.Callback() {
            @Override
            public void onSuccess() {
                createSuccess.postValue(true);
                message.postValue("Tạo dự án thành công!");
            }
            @Override
            public void onError(String msg) {
                createSuccess.postValue(false);
                message.postValue(msg);
            }
        });
    }
    public LiveData<ProjectWithStats> getProjectStats(long projectId) {
        return getProjectStatsUseCase.execute(projectId);
    }
    public void deleteProject(Project project) {
        deleteProjectUseCase.execute(project);
        message.postValue("Đã xóa dự án: " + project.getName());
    }
}
