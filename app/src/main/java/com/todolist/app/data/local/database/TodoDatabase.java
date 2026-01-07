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
                Milestone.class,
                ActivityLog.class,
                Tag.class,
                TaskTag.class
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
    public abstract CategoryDao categoryDao();
    public abstract ActivityLogDao activityLogDao();

    public static TodoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TodoDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            // THÊM ĐOẠN CALLBACK NÀY ĐỂ TỰ CHÈN DỮ LIỆU
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Chèn 4 danh mục mẫu đúng như file Word
                                    db.execSQL("INSERT INTO categories (category_id, name) VALUES (1, 'Công việc')");
                                    db.execSQL("INSERT INTO categories (category_id, name) VALUES (2, 'Học tập')");
                                    db.execSQL("INSERT INTO categories (category_id, name) VALUES (3, 'Cá nhân')");
                                    db.execSQL("INSERT INTO categories (category_id, name) VALUES (4, 'Mua sắm')");
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}