package com.todolist.app.presentation.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.todolist.app.R;
import com.todolist.app.presentation.ui.task.TaskListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Mẹo: Dùng android.R.id.content để lấy toàn bộ màn hình làm khung chứa Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new TaskListFragment())
                    .commitNow();
        }
    }
}