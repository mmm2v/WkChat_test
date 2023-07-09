package com.weike.test.jni;

import android.content.Context;
import android.util.Log;

import com.weike.test.utils.ThreadUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CppCallJava {

    private final String TAG = "----c++回调----";

    public CppCallJava() {
        Log.d(TAG, "CppCallJavaClass构造方法调用: ");
    }

    /**
     * 发送udp数据包
     * @param msg 数据包byte数组
     * @param msgLen 数组长度
     */
    public void sendUdpMsg(byte[] msg, int msgLen) {
        ThreadUtils.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                DatagramSocket sendSocket = null;
                try {
//                    for (byte b: msg) {
//                        Log.d(TAG, "byte: -----" + String.format("%02X", b));
//                    }
                    sendSocket = new DatagramSocket(5000);
                    // 服务器地址
                    String csIp = "192.168.1.28";
                    InetAddress inetAddress = InetAddress.getByName(csIp);
                    // 服务器端口
                    int csPort = 12200;
                    sendSocket.send(new DatagramPacket(msg, msgLen, inetAddress, csPort));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    assert sendSocket != null;
                    sendSocket.close();
                }
            }
        });
    }

    /**
     * 注册成功
     *
     * @param context 上下文
     */
    public void registerOk(Context context) {
        Log.d(TAG, "---注册成功---");
    }

    /**
     * 呼入振铃
     *
     * @param context 上下文
     */
    public void ring(Context context) {
        Log.d(TAG, "---呼入振铃---");
    }

    /**
     * 呼叫成功
     *
     * @param context 上下文
     */
    public void callOk(Context context) {
        Log.d(TAG, "---呼叫成功---");
    }

    /**
     * 对方挂机
     *
     * @param context 上下文
     */
    public void byeO(Context context) {
        Log.d(TAG, "---对方挂机---");
    }

    /**
     * 呼叫失败
     *
     * @param context 上下文
     */
    public void callErr(Context context) {
        Log.d(TAG, "---呼叫失败---");
    }

    /**
     * 注册err
     *
     * @param context 上下文
     */
    public void registerErr(Context context) {
        Log.d(TAG, "---注册err---");
    }

    /**
     * ip没获取到
     *
     * @param context 上下文
     */
    public void netErr(Context context) {
        Log.d(TAG, "---ip没获取到---");
    }

    /**
     *
     * @param context 上下文
     */
    public void netOk(Context context) {
        Log.d(TAG, "---netOk---");
    }

    /**
     * mac地址变化
     *
     * @param context 上下文
     */
    public void macChange(Context context) {
        Log.d(TAG, "---mac地址变化---");
    }

    /**
     * 信息发送成功
     *
     * @param context 上下文
     */
    public void smSndOk(Context context) {
        Log.d(TAG, "---信息发送成功---");
    }

    public String recordResult() {
        return "----返回结果----";
    }

    public void test() {
        Log.d(TAG, "-----------test:----------");
    }
}
