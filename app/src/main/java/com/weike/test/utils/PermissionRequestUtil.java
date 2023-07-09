package com.weike.test.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionRequestUtil {


    // 请求权限的请求码
    private static final int PERMISSION_REQUEST_CODE = 100;

    // 检查是否拥有指定权限
    public static boolean hasPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    // 检查是否拥有多个权限
    public static boolean hasPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 请求权限
    public static void requestPermissions(Activity activity, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static void checkDefaultPermission(Activity activity){
        String[] PERMISSIONS = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.VIBRATE,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                Manifest.permission.DISABLE_KEYGUARD
        };
        requestPermissions(activity, PERMISSIONS);
    }

    public static void requestOverlay(Activity activity){
        if(!Settings.canDrawOverlays(activity)){
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            activity.startActivity(intent);
        }
    }
}
