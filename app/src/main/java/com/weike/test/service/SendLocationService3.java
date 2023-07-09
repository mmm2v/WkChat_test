package com.weike.test.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.weike.test.R;

public class SendLocationService3 extends AccessibilityService {

    private final String CHANNEL_ID = "ChannelId";
    private final String CHANNEL_NAME = "ChannelName";

    private String TAG = "00000000";


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        showNotification(this, "启动服务");
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent: ");
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt: ");
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.d(TAG, "onKeyEvent: " + event.getKeyCode());
        if (KeyEvent.ACTION_DOWN == event.getAction()){
            Toast.makeText(getApplicationContext().getApplicationContext(), "按下", Toast.LENGTH_LONG).show();
        }
        if (KeyEvent.ACTION_UP == event.getAction()){
            Toast.makeText(getApplicationContext().getApplicationContext(), "松开", Toast.LENGTH_LONG).show();
        }
        return super.onKeyEvent(event);
    }

    @SuppressLint("MissingPermission")
    public void showNotification(Context context, String message) {
        // 创建channel
        createNotificationChannel(context);
        // 创建通知构建器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("维科通话")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        startForeground(1,builder.build());//这个就是之前说的startForeground
    }

    private void createNotificationChannel(Context context) {
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
