package com.todolist.app.presentation.ui.focus;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.todolist.app.R;

public class FocusModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Liên kết với layout activity_focus_mode.xml
        //setContentView(R.layout.activity_focus_mode);

        // Ẩn Action Bar để tạo cảm giác tập trung (Focus Mode)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}