package com.todolist.app.presentation.ui.project;

import static java.security.AccessController.getContext;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.todolist.app.R;
import com.todolist.app.presentation.di.ViewModelFactory;
import com.todolist.app.presentation.ui.common.BaseFragment;
import com.todolist.app.presentation.ui.project.adapter.ProjectAdapter;

public class ProjectListFragment extends BaseFragment {
    private ProjectViewModel viewModel;
    private ProjectAdapter adapter;
    private RecyclerView rvProjects;
    private FloatingActionButton fabAdd;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ProjectViewModel.class);
        rvProjects = view.findViewById(R.id.rv_projects);
        rvProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectAdapter();
        rvProjects.setAdapter(adapter);
        fabAdd = view.findViewById(R.id.fab_add_project);
        fabAdd.setOnClickListener(this::onClick);
        viewModel.getActiveProjects().observe(getViewLifecycleOwner(), projects -> {
            adapter.setProjects(projects);
        });
        adapter.setOnItemClickListener(project -> {
            ProjectDetailFragment fragment = new ProjectDetailFragment();
            Bundle args = new Bundle();
            args.putLong("PROJECT_ID", project.getProjectId());
            fragment.setArguments(args);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private AndroidViewModel requireActivity() {
        return null;
    }

    private @org.jspecify.annotations.NonNull LifecycleOwner getViewLifecycleOwner() {
        return null;
    }

    private SQLiteDatabase getParentFragmentManager() {
        return null;
    }


}
