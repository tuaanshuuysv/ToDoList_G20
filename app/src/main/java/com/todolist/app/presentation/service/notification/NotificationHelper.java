package com.todolist.app.presentation.service.notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.todolist.app.presentation.ui.main.MainActivity;

/**
 * Helper hỗ trợ tạo và hiển thị thông báo.
 * Đảm bảo thông báo xuất hiện đúng Channel và có độ ưu tiên cao.
 */
public class NotificationHelper {
    private static final String CHANNEL_ID = "todo_reminder_channel";
    private static final String CHANNEL_NAME = "Lịch nhắc nhở công việc";

    public static void showNotification(Context context, String title, String message) {
        Log.d("DEBUG_NOTI", "Hàm showNotification đã được gọi: " + title);

        // 1. KIỂM TRA QUYỀN (Dành cho Android 13 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("DEBUG_NOTI", "Chưa có quyền POST_NOTIFICATIONS");
                return;
            }
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) return;

        // 2. KHỞI TẠO CHANNEL (Dành cho Android 8.0 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Thông báo nhắc nhở công việc từ Todo List");
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.enableVibration(true);

            // Gửi Channel lên hệ thống
            notificationManager.createNotificationChannel(channel);
        }

        // 3. THIẾT LẬP HÀNH ĐỘNG KHI CHẠM (PendingIntent)
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                (int) System.currentTimeMillis(), // Dùng timestamp để Intent luôn mới
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 4. XÂY DỰNG NỘI DUNG THÔNG BÁO (Notification Builder)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // Icon mặc định hệ thống
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)                                // Chạm vào tự biến mất
                .setPriority(NotificationCompat.PRIORITY_HIGH)      // Hiện dạng biểu ngữ (heads-up)
                .setDefaults(NotificationCompat.DEFAULT_ALL)        // Rung và chuông mặc định
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        // 5. HIỂN THỊ THÔNG BÁO
        // ID duy nhất giúp các thông báo không bị đè lên nhau nếu nổ liên tiếp
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}