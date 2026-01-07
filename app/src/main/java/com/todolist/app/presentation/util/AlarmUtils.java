package com.todolist.app.presentation.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.todolist.app.presentation.service.notification.NotificationReceiver;

/**
 * Utility giúp quản lý việc đặt và hủy báo thức hệ thống.
 * Đảm bảo thông báo xuất hiện đúng giờ ngay cả khi thiết bị đang ở chế độ ngủ (Doze Mode).
 */
public class AlarmUtils {

    private static final String TAG = "AlarmUtils";

    /**
     * Hàm để đặt báo thức nhắc nhở
     * @param context Ngữ cảnh ứng dụng
     * @param timeInMillis Thời gian muốn nhắc (dạng Long)
     * @param taskTitle Tiêu đề công việc để hiện lên thông báo
     * @param taskId ID của Task (dùng làm requestCode để định danh báo thức)
     */
    public static void setAlarm(Context context, long timeInMillis, String taskTitle, int taskId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        // 1. Chuẩn bị Intent gửi tới NotificationReceiver
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("TASK_TITLE", taskTitle);

        // requestCode = taskId giúp mỗi Task có một báo thức riêng, không bị đè lên nhau
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                taskId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 2. Xử lý đặt báo thức theo từng phiên bản Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+ yêu cầu quyền SCHEDULE_EXACT_ALARM để dùng hàm setExact
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
                Log.d(TAG, "Đã đặt báo thức CHÍNH XÁC cho Task: " + taskTitle);
            } else {
                // Nếu chưa có quyền, dùng hàm set thường (có thể lệch vài phút nhưng không crash)
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
                Log.w(TAG, "Chưa có quyền báo thức chính xác, sử dụng chế độ tiết kiệm pin.");
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 đến 11: Cho phép chạy ngay cả khi máy đang ngủ sâu (Doze Mode)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        } else {
            // Các bản Android cũ hơn
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }

    /**
     * Hàm để hủy báo thức (Sử dụng khi xóa Task hoặc người dùng tắt nhắc nhở)
     * Lưu ý: Intent và taskId phải khớp hoàn toàn với lúc đặt mới hủy được.
     */
    public static void cancelAlarm(Context context, int taskId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                taskId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "Đã hủy báo thức của Task ID: " + taskId);
    }
}