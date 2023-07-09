package com.weike.test.socket;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.weike.test.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "MyChannelId";
    private static final String CHANNEL_NAME = "MyChannelName";
    private static final int NOTIFICATION_ID = 1;

    private static final String TAG = "tag4";

    @SuppressLint("MissingPermission")
    public static void showNotification(Context context, String message) {
        // 创建channel
        createNotificationChannel(context);
        Log.d(TAG, "showNotification: 通知--------------");

        // 创建通知构建器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("维科通话")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        // 发送通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private static void createNotificationChannel(Context context) {
        // 在 Android 8.0 及以上版本创建通知渠道
        String channelDescription = "维科通话通知渠道";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = null;
        channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(channelDescription);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setShowBadge(true);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
