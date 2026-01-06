package com.todolist.app.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.todolist.app.data.local.dao.*;
import com.todolist.app.data.local.entity.*;

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
    public abstract TaskDao taskDao();
    public abstract SubtaskDao subtaskDao();
    public abstract ProjectDao projectDao();
    public abstract MilestoneDao milestoneDao();
    public abstract CategoryDao categoryDao();
    public static TodoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TodoDatabase.class,
                                    DATABASE_NAME
                            )
                            .addCallback(new DatabaseCallback(context))
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
