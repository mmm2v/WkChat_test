package com.weike.test.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AudioReceiver {
    private static final int PORT = 5000;

    private DatagramSocket socket;
    private byte[] buffer;
    private boolean isReceiving = false;

    public void startReceiving() {
        try {
            // 创建 DatagramSocket
            socket = new DatagramSocket(PORT);

            // 启动接收音频数据
            isReceiving = true;
            buffer = new byte[1024]; // 根据实际需求设置合适的缓冲区大小

            while (isReceiving) {
                // 创建 DatagramPacket 并接收数据
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // 处理接收到的音频数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopReceiving();
        }
    }

    public void stopReceiving() {
        isReceiving = false;

        if (socket != null) {
            socket.close();
            socket = null;
        }
    }
}
