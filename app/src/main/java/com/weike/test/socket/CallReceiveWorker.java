package com.weike.test.socket;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alibaba.fastjson.JSON;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CallReceiveWorker extends Worker {
    private String TAG = "Worker--------";

    DatagramSocket receiveSocket;

    public CallReceiveWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "执行doWork: ");
        int i = 0;
        try {
            receiveSocket = new DatagramSocket(9876);
            while (!isStopped()) {
                Log.d(TAG, "doWork: 循环" + i++);
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);

                receiveSocket.receive(datagramPacket);

                Logger.getLogger("...SocketReceiveService...").log(Level.INFO, "receiveMsg: " + JSON.parse(datagramPacket.getData()));
                Context context = getApplicationContext();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        } finally {
            if (receiveSocket != null) {
                receiveSocket.close();
            }
        }
        return Result.success();
    }
}

