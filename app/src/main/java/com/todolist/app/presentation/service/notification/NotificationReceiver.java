package com.todolist.app.presentation.service.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadcastReceiver nhận tín hiệu báo thức từ AlarmManager.
 * Đây là cầu nối giữa hệ thống Android và NotificationHelper của ứng dụng.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DEBUG_NOTI", "NotificationReceiver: Đã nhận được tín hiệu báo thức!");

        // 1. Giải mã dữ liệu từ Intent được gửi tới
        String taskTitle = intent.getStringExtra("TASK_TITLE");

        // Kiểm tra tiêu đề, nếu null thì dùng nội dung mặc định
        if (taskTitle == null || taskTitle.isEmpty()) {
            taskTitle = "Bạn có công việc cần hoàn thành ngay!";
        }

        // 2. Kích hoạt hiển thị thông báo thông qua Helper
        // Tiêu đề thông báo thường cố định để người dùng nhận diện app
        // Nội dung thông báo chính là tên công việc (Task Title)
        NotificationHelper.showNotification(
                context,
                "Nhắc nhở Todo List",
                taskTitle
        );
    }
}