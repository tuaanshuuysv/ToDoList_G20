package com.todolist.app.presentation.ui.project.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todolist.app.data.local.entity.Task;
import com.todolist.app.presentation.ui.home.adapter.TaskAdapter;

import java.util.List;
public class ProjectTimelineView extends FrameLayout {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    public ProjectTimelineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        addView(recyclerView);
    }
    public void setTasks(List<Task> tasks) {
        if (adapter != null) {
            adapter.setTasks(tasks);
        }
    }
}
