package com.todolist.app.presentation.ui.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.todolist.app.R;
import com.todolist.app.data.local.entity.relations.ProjectWithTasks;
import com.todolist.app.presentation.di.ViewModelFactory;
import com.todolist.app.presentation.ui.common.BaseFragment;
import com.todolist.app.presentation.ui.project.view.ProjectCalendarView;
import com.todolist.app.presentation.ui.project.view.ProjectKanbanView;
import com.todolist.app.presentation.ui.project.view.ProjectListView;
import com.todolist.app.presentation.ui.project.view.ProjectTimelineView;

public class ProjectDetailFragment extends BaseFragment {
    private ProjectViewModel viewModel;
    private long projectId = -1;
    private FrameLayout viewContainer;
    private TextView tvTitle;
    private ProjectListView listView;
    private ProjectKanbanView kanbanView;
    private ProjectCalendarView calendarView;
    private ProjectTimelineView timelineView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getLong("PROJECT_ID", -1);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_detail, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ProjectViewModel.class);
        viewContainer = view.findViewById(R.id.project_view_container);
        tvTitle = view.findViewById(R.id.tv_detail_title);
        if (projectId != -1) {
            viewModel.getProjectStats(projectId).observe(getViewLifecycleOwner(), stats -> {
                if (stats != null && stats.project != null) {
                    tvTitle.setText(stats.project.getName());
                }
            });
            showListView();
        }
    }
    private void showListView() {
        viewContainer.removeAllViews();
        if (listView == null) {
            listView = new ProjectListView(getContext(), null);
        }
        viewContainer.addView(listView);
    }
}
