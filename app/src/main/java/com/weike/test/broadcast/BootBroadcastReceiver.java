package com.weike.test.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.weike.test.service.SendLocationService;
import com.weike.test.test.TestActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BootBroadcastReceiver extends BroadcastReceiver {


    String TAG = "33333333";
    String PTT_DOWN = "android.intent.action.PTT.down";
    String ACTION = "android.intent.action.BOOT_COMPLETED";
    String REBOOT = "android.intent.action.REBOOT";
    String PTT_UP = "android.intent.action.PTT.up";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: "+intent.getAction());
        String SERVER_IP = "192.168.1.31"; // 服务器IP地址
        int SERVER_PORT = 12345; // 服务器端口号
        DatagramSocket sendSocket = null;
        try {
            sendSocket = new DatagramSocket(SERVER_PORT);
            InetAddress inetAddress = InetAddress.getByName(SERVER_IP);
            byte[] bytes = "1111".getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, SERVER_PORT);
            sendSocket.send(datagramPacket);
            System.out.println("MainActivity = " + "发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(intent.getAction().equals(PTT_DOWN)) {
            Toast.makeText(context, "收到消息", Toast.LENGTH_SHORT).show();
            Intent service = new Intent(context, SendLocationService.class);
            context.startForegroundService(service);

            Log.d(TAG, "开机自动服务启动...");
            Toast.makeText(context, "已启动服务", Toast.LENGTH_SHORT).show();
            Intent intentTest = context.getPackageManager().getLaunchIntentForPackage("com.weike.test");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                intentTest = new Intent(context, TestActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentTest);
        }
        Log.d(TAG, "3333333333333: "+ intent.getAction().equals(ACTION));
        if(intent.getAction().equals(ACTION)) {
            Toast.makeText(context, "收到消息", Toast.LENGTH_SHORT).show();
            Intent service = new Intent(context, SendLocationService.class);
            context.startForegroundService(service);

            Log.d(TAG, "开机自动服务启动...");
            Toast.makeText(context, "已启动服务", Toast.LENGTH_SHORT).show();
            Intent intentTest = context.getPackageManager().getLaunchIntentForPackage("com.weike.test");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                intentTest = new Intent(context, TestActivity.class);
            }
            intentTest.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentTest);
        }

    }
}
