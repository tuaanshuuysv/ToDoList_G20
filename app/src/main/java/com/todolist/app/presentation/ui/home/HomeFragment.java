package com.todolist.app.presentation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * HomeFragment - TODO: Member 1 will implement this
 */
public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // TODO: Member 1 - Inflate proper layout
        // return inflater.inflate(R. layout.fragment_home, container, false);

        // Temporary:  Return empty view to avoid crash
        View view = new View(requireContext());
        return view;
    }
}