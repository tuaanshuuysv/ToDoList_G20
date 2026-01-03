package com.todolist. app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.todolist.app.presentation. util.Constants;

/**
 * TodoApplication - Main Application Class
 *
 * TODO: Member 4 - Complete initialization
 */
public class TodoApplication extends Application {

    private static TodoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Create notification channels
        createNotificationChannels();

        // TODO: Member 4 - Initialize database here if needed
        // TodoDatabase.getInstance(this);
    }

    public static TodoApplication getInstance() {
        return instance;
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    Constants.CHANNEL_ID,
                    Constants.CHANNEL_NAME,
                    NotificationManager. IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for task reminders and updates");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}