//package com.todolist.app.presentation.di;
//
//import android.content.Context;
//import androidx.room.Room;
//import com.todolist.app.data.local.dao.ActivityLogDao;
//import com.todolist.app.data.local.dao.TaskDao;
//import com.todolist.app.data.local.database.TodoDatabase;
//import javax.inject.Singleton;
//import dagger.Module;
//import dagger.Provides;
//import dagger.hilt.InstallIn;
//import dagger.hilt.android.qualifiers.ApplicationContext;
//import dagger.hilt.components.SingletonComponent;
//
//@Module
//@InstallIn(SingletonComponent.class)
//public class DatabaseModule {
//
//    @Provides
//    @Singleton
//    public static TodoDatabase provideDatabase(@ApplicationContext Context context) {
//        return Room.databaseBuilder(
//                context,
//                TodoDatabase.class,
//                "todo_database"
//        ).fallbackToDestructiveMigration().build();
//    }
//
//    @Provides
//    @Singleton
//    public static TaskDao provideTaskDao(TodoDatabase database) {
//        return database.taskDao();
//    }
//
//    // Sau này các bạn khác sẽ thêm các Dao khác vào đây
//    @Provides
//    @Singleton
//    public static ActivityLogDao provideActivityLogDao(TodoDatabase database) {
//        return database.activityLogDao();
//    }
//}