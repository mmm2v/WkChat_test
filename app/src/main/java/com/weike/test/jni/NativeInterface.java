package com.weike.test.jni;

public class NativeInterface {

    public native void proSignalling(byte[] buf, int len);

    /**
     * 初始化
     * @param myIp 本机ip
     * @param lSignallingPort 本机信令端口
     * @param lVoicePort 本机语音端口
     */
    public native void init(long csIp, long myIp, char signallingPort, char lSignallingPort, char lVoicePort);

    /**
     * ip地址变更
     * @param myIp 本机ip
     */
    public native void ipChange(int myIp);

    /**
     * 注册
     * @param user 用户名
     * @param pwd 密码
     * @param regFlag
     */
    public native void regnot(String user, String pwd, boolean regFlag);

    /**
     * 通话请求
     * @param called
     */
    public native void callReq(String called);

    /**
     * 通话结束
     */
    public native void callOver();

    /**
     * 摘机
     */
    public native void offHook();

    /**
     * 发送消息
     * @param called
     * @param sm
     */
    public native void sendSm(String called, String sm);

}
