package com.todolist.app.presentation.ui.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.todolist.app.R; // Đảm bảo import đúng R của package bạn

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Liên kết với layout activity_settings.xml
        //setContentView(R.layout.activity_settings);

        // Thiết lập tiêu đề cho Action Bar nếu cần
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Cài đặt");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Nút back
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}