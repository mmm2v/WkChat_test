package com.weike.test.test;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;

import com.weike.test.R;

public class SensorTestActivity extends Activity implements SensorEventListener {

    private static final String TAG = "tag3";

    private TextView tv;
    private Sensor proximitySensor;

    // 光线传感器
    private Sensor lightSensor;

    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_test);

        tv = findViewById(R.id.test_result);
        // 1.获取SensorManager管理对象
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 2.获取据传感器对象
        lightSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximitySensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 处理距离传感器数据变化事件
        float value = event.values[0];
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY:
                // 根据距离做相应处理
                //获取最大距离getMaximumRange（）,系统固定值
                float maxAccuracy = proximitySensor.getMaximumRange();
                Log.d(TAG, "距离: " + value);
                Log.d(TAG, "最大距离: " + maxAccuracy);
                if (value < 100) {
                    // 距离小于最大范围，表示手机靠近物体，执行息屏操作
                    turnOffScreen();
                } else {
                    // 距离大于最大范围，表示手机远离物体，执行亮屏操作
                    turnOnScreen();
                }
                break;
            case Sensor.TYPE_LIGHT:
//                Log.d(TAG, "光线强度: " + value);
//                float maxBrightness = 255f; // 设置最大亮度值
//
//                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//                float rate = value / maxBrightness;
//                Log.d(TAG, "比值: " + rate);
//                layoutParams.screenBrightness = rate;
//                getWindow().setAttributes(layoutParams);
                break;
            default:
                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 距离传感器准确度变化回调
    }

    private void turnOffScreen() {
        // 执行息屏操作，例如使用 PowerManager 设置屏幕休眠
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && powerManager.isScreenOn()) {
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                    PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "tag3: screen off");
            wakeLock.acquire(60 * 1000L /*1 minutes*/);
        }
    }

    private void turnOnScreen() {
        // 执行亮屏操作，例如使用 PowerManager 清除屏幕休眠
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && !powerManager.isInteractive()) {
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE,
                    "tag3: screen on");
            wakeLock.acquire(3000); // 持续亮屏时间（毫秒）
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
//        3.给Sensor注册监听事件
        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        sm.unregisterListener(this, lightSensor);
        sm.unregisterListener(this, proximitySensor);
    }
}

