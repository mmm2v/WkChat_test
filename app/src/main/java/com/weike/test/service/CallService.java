package com.weike.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class CallService extends Service {
    private static final String TAG = "-------悬浮-----";

    private static CallService instance;

    // 1、呼出 2、通话 3、呼入
    private String type;

    private String name;
    private String number;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d(TAG, "onCreate: " + instance);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 计时
        type = intent.getStringExtra("type");
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        Log.d(TAG, "-------onstartcommand------type" +type+ ",name:"+name+",number:"+number+",callTime");

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
