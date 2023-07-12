package com.weike.test.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AudioSender {
    private static final int PORT = 5000;

    private DatagramSocket socket;
    private InetAddress destinationAddress;
    private int destinationPort;
    private boolean isSending = false;

    public void startSending(String destinationIP) {
        try {
            // 创建 DatagramSocket
            socket = new DatagramSocket();

            // 设置目标地址和端口
            destinationAddress = InetAddress.getByName(destinationIP);
            destinationPort = PORT;

            // 启动发送音频数据
            isSending = true;
            byte[] buffer = new byte[1024]; // 根据实际需求设置合适的缓冲区大小

            while (isSending) {
                // 读取音频数据到 buffer 中

                // 创建 DatagramPacket 并发送数据
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, destinationAddress, destinationPort);
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopSending();
        }
    }

    public void stopSending() {
        isSending = false;

        if (socket != null) {
            socket.close();
            socket = null;
        }
    }
}
