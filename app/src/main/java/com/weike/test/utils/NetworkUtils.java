package com.weike.test.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkUtils {
    public static ArrayList<String> getIPAddress() {
        ArrayList<String> adds = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    //此条件可获取到需要的IP地址关键代码：address.isSiteLocalAddress()
                    if (address instanceof Inet4Address && !address.isLoopbackAddress() && !address.isLinkLocalAddress() && address.isSiteLocalAddress()) {
                        adds.add(address.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adds;
    }
    public static int ip2Int(String ipString) {
        // 取 ip 的各段
        String[] ipSlices = ipString.split("\\.");
        List<String> a = new ArrayList();
        for (int i = ipSlices.length-1; i >= 0; i--) {
            a.add(ipSlices[i]);
        }
        int rs = 0;
        for (int i = 0; i < a.size(); i++) {
            // 将 ip 的每一段解析为 int，并根据位置左移 8 位
            int intSlice = Integer.parseInt(a.get(i)) << 8 * i;
            // 或运算
            rs = rs | intSlice;
        }
        return rs;
    }

    public static String getPrivateIPAddress(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Network network = connectivityManager.getActiveNetwork();
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    // 当前连接为WiFi网络
                    return getWifiIPAddress(context);
                }
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // 当前连接为WiFi网络
                    return getWifiIPAddress(context);
                }
            }
        }

        return null;
    }

    private static String getWifiIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();

            // 将IP地址转换为字符串
            String ipString = String.format(
                    "%d.%d.%d.%d",
                    (ipAddress & 0xff),
                    (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff)
            );

            return ipString;
        } else {
            Log.e("IP Address", "WiFi is disabled");
            return null;
        }
    }
}