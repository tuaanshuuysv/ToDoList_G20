package com.todolist.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.todolist.app.data.local.dao.ProjectDao;
import com.todolist.app.data.local.database.TodoDatabase;
import com.todolist.app.data.local.entity.Project;
import com.todolist.app.data.local.entity.relations.ProjectWithStats;
import com.todolist.app.data.local.entity.relations.ProjectWithTasks;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectRepository {
    private final ProjectDao projectDao;
    private final ExecutorService executorService;
    public ProjectRepository(Application application) {
        TodoDatabase db = TodoDatabase.getInstance(application);
        this.projectDao = db.projectDao();
        this.executorService = Executors.newFixedThreadPool(4);
    }
    public LiveData<List<Project>> getAllActiveProjects() {
        return projectDao.getAllActiveProjects();
    }
    public LiveData<ProjectWithTasks> getProjectWithTasks(long projectId) {
        return projectDao.getProjectWithTasks(projectId);
    }
    public LiveData<ProjectWithStats> getProjectStats(long projectId) {
        return projectDao.getProjectStats(projectId);
    }
    public void insert(Project project) {
        executorService.execute(() -> projectDao.insertProject(project));
    }
    public void update(Project project) {
        executorService.execute(() -> projectDao.updateProject(project));
    }
    public void delete(Project project) {
        executorService.execute(() -> projectDao.deleteProject(project));
    }
    public boolean isProjectNameExists(String name) {
        return projectDao.checkProjectNameExists(name) > 0;
    }
}
