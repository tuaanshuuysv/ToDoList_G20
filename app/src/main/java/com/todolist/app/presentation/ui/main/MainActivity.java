package com.todolist.app.presentation.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.todolist.app.R; // Import Resource

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}