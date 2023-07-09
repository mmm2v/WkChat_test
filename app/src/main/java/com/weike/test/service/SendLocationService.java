package com.weike.test.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.weike.test.R;
import com.weike.test.common.CommonConsts;
import com.weike.test.test.TestActivity;

public class SendLocationService extends Service {

    InnerReceiver innerReceiver;

    private final String CHANNEL_ID = "ChannelId";
    private final String CHANNEL_NAME = "ChannelName";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        innerReceiver = new InnerReceiver();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PTT.down");
        IntentFilter intentFilter1 = new IntentFilter("android.intent.action.PTT.up");
        registerReceiver(innerReceiver, intentFilter);
        registerReceiver(innerReceiver, intentFilter1);
        showNotification(this, "启动服务");
    }

    @SuppressLint("MissingPermission")
    public void showNotification(Context context, String message) {
        // 创建channel
        createNotificationChannel(context);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.weike.test");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            intent = new Intent(context, TestActivity.class);
        }
        intent.putExtra("name", message);
        intent.putExtra("number", message);
        intent.putExtra("type", CommonConsts.CALL_IN_RING);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 创建通知构建器
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("维科通话")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
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

    @Override
    public void onDestroy() {
        unregisterReceiver(innerReceiver);
        innerReceiver = null;
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isRunning", false).apply();
        super.onDestroy();
    }

    public static class InnerReceiver extends BroadcastReceiver{
        String ACTION_DOWN = "android.intent.action.PTT.down";
        String ACTION_UP = "android.intent.action.PTT.up";

        String ACTION_GET_STATUS = "android.intent.action.GET.STATUS";
        long preTime;
        int clickCounts = 0;
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();

            if(ACTION_DOWN.equals(action)) {
                preTime = System.currentTimeMillis();
                Log.d("33333333", "按下");
                Toast.makeText(context.getApplicationContext(), "按下", Toast.LENGTH_LONG).show();
            }else if(ACTION_UP.equals(action)) {
                clickCounts += 1;
                if(System.currentTimeMillis() - preTime > 3000) {
                    clickCounts = 0;
                }else{
                    Log.d("33333333", "松开");
                    Toast.makeText(context.getApplicationContext(), "松开", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
