package com.weike.test.socket;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketThread extends Thread {
    private final Socket socket;

    private final Context context;

    public SocketThread(Context context, Socket socket) {
        this.socket = socket;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            // 获取输入流
            InputStream inputStream = socket.getInputStream();
            // 读取数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String message = reader.readLine();
            // 在这里处理接收到的数据，可以通过回调或发送广播等方式通知其他组件
            Log.d("tag1", "message:" + message);
            AutoUnlockAndNotifyUtil.autoUnlockAndNotify(context, message);
            // 关闭连接
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
