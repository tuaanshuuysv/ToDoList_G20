package com.todolist.app.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.todolist.app.data.local.dao.*;
import com.todolist.app.data.local.entity.*;

/**
 * TodoDatabase - Room Database Configuration
 * <p>
 * TODO for team members:
 * - Member 1: Add TaskDao, SubtaskDao
 * - Member 2: Add ProjectDao, MilestoneDao
 * - Member 3: Add CategoryDao
 * - Member 4: Complete getInstance() and initialization
 */
@Database(
        entities = {
                Task.class,
                Project.class,
                Category.class,
                Subtask.class,
                Milestone.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class TodoDatabase extends RoomDatabase {

    private static volatile TodoDatabase INSTANCE;
    private static final String DATABASE_NAME = "todo_database";

    // ===========================================
    // DAOs - TODO: Add by team members
    // ===========================================

    // TODO: Member 1 - Add these methods
    // public abstract TaskDao taskDao();
    // public abstract SubtaskDao subtaskDao();

    // TODO: Member 2 - Add these methods
    // public abstract ProjectDao projectDao();
    // public abstract MilestoneDao milestoneDao();

    // TODO: Member 3 - Add this method
    // public abstract CategoryDao categoryDao();


    // ===========================================
    // Singleton Instance
    // ===========================================

    /**
     * TODO: Member 4 - Implement this method
     * <p>
     * Example implementation:
     * <p>
     * public static TodoDatabase getInstance(Context context) {
     * if (INSTANCE == null) {
     * synchronized (TodoDatabase.class) {
     * if (INSTANCE == null) {
     * INSTANCE = Room. databaseBuilder(
     * context.getApplicationContext(),
     * TodoDatabase.class,
     * DATABASE_NAME
     * )
     * .addCallback(new DatabaseCallback())
     * . fallbackToDestructiveMigration()
     * . build();
     * }
     * }
     * }
     * return INSTANCE;
     * }
     */
    public static TodoDatabase getInstance(Context context) {
        // TODO: Member 4 - Implement singleton pattern here
        return null;  // Temporary
    }
}