package com.weike.test.socket;

import android.content.Context;
import android.os.PowerManager;

public class AutoUnlockAndNotifyUtil {
    public static void autoUnlockAndNotify(Context context, String notificationText) {
        // 亮屏
        wakeUpScreen(context);
        // 通知
        NotificationHelper.showNotification(context, notificationText);
    }

    public static void autoUnlockAndNotify(Context context) {
        // 亮屏
        wakeUpScreen(context);
    }

    private static void wakeUpScreen(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && !powerManager.isInteractive()) {
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE,
                    "AutoUnlockAndNotifyUtil:WakeLock");
            wakeLock.acquire(3000); // 持续亮屏时间（毫秒）
        }
    }
}

