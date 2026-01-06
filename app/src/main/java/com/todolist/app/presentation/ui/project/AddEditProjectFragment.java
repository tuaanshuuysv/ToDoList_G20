package com.todolist.app.presentation.ui.project;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.todolist.app.R;
import com.todolist.app.presentation.di.ViewModelFactory;
import com.todolist.app.presentation.ui.common.BaseFragment;

import java.util.Date;

public class AddEditProjectFragment extends BaseFragment {
    private ProjectViewModel viewModel;
    private EditText edtName, edtDesc;
    private Button btnSave;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_project, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ProjectViewModel.class);
        edtName = view.findViewById(R.id.edt_project_name);
        edtDesc = view.findViewById(R.id.edt_project_description);
        btnSave = view.findViewById(R.id.btn_save_project);
        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String desc = edtDesc.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập tên dự án", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.createProject(name, desc, "#2196F3", "icon_folder", new Date());
        });
        viewModel.getCreateSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Tạo dự án thành công!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack(); // Quay lại màn hình trước
            }
        });
        viewModel.getMessage().observe(getViewLifecycleOwner(), msg -> {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }
    private FragmentManager getParentFragmentManager() {
        return null;
    }
    private AndroidViewModel requireActivity() {
        return null;
    }
    private @org.jspecify.annotations.NonNull LifecycleOwner getViewLifecycleOwner() {
        return null;
    }
}
