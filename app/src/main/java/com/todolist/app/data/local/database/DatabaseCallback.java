package com.todolist.app.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

/**
 * DatabaseCallback - Seed default data
 * <p>
 * TODO:  Member 3 can add seed categories here
 */
public class DatabaseCallback extends RoomDatabase.Callback {

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);

        // Seed default categories
        Executors.newSingleThreadExecutor().execute(() -> {
            // TODO: Member 3 - Add seed data for categories
            /*
            db.execSQL("INSERT INTO categories (name, color, icon, created_at) VALUES " +
                "('Công việc', '#F44336', 'work', " + System.currentTimeMillis() + "), " +
                "('Học tập', '#2196F3', 'school', " + System.currentTimeMillis() + "), " +
                "('Cá nhân', '#4CAF50', 'person', " + System.currentTimeMillis() + "), " +
                "('Mua sắm', '#FFC107', 'shopping_cart', " + System.currentTimeMillis() + ")");
            */
        });
    }
}