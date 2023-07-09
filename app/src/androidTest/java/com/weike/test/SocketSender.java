package com.weike.test;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketSender {
    private static final String SERVER_IP = "192.168.1.35"; // 服务器IP地址
    private static final int SERVER_PORT = 11111; // 服务器端口号

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化日期时间为指定格式的字符串
        String message = "hello android, 1时间是：" + dateFormat.format(new Date());
        sendSocketData(message);
    }

    public static void sendSocketData(String message) {
        try {
            // 创建Socket连接到服务器
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            // 获取输出流
            OutputStream outputStream = socket.getOutputStream();

            // 发送数据给服务器
            outputStream.write(message.getBytes());
            outputStream.close();

            // 关闭连接
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
