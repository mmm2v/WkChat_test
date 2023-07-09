package com.weike.test.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.weike.test.R;
import com.weike.test.socket.CallReceiveWorker;

public class CallReceiveService extends Service {
    public CallReceiveService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(1, createNotification());
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED) // 设置网络连接类型的约束条件，例如仅在WIFI连接时执行任务
                .build();

        OneTimeWorkRequest receiveWorkRequest = new OneTimeWorkRequest.Builder(CallReceiveWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(receiveWorkRequest);
        return START_STICKY;
    }

    private Notification createNotification() {
        NotificationChannel channel = new NotificationChannel("1", "消息通知", NotificationManager.IMPORTANCE_DEFAULT);
        // 发送通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.createNotificationChannel(channel);
        // 构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setContentTitle("消息通知")
                .setContentText("正在接收消息")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(false)
                .setOngoing(true);

        return builder.build();
    }


//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        ThreadUtils.getInstance().executeTask(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true){
//                        byte[] bytes = new byte[1024];
//                        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
//                        receiveSocket.receive(datagramPacket);
//                        Logger.getLogger("...SocketReceiveService...").log(Level.INFO, "receiveMsg: " + JSON.parse(datagramPacket.getData()));
//                        Context context = getApplicationContext();
//                        HistoryEntity entity = new HistoryEntity();
//                        entity.setName(JSON.parse(datagramPacket.getData()).toString());
//                        entity.setNumber(JSON.parse(datagramPacket.getData()).toString());
//                        if (!CallService.isServiceRun()) {
//                            AutoUnlockAndNotifyUtil.autoUnlockAndNotify(context, entity);
//                        } else {
//                            Toast.makeText(context, "通话中", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (receiveSocket != null) {
//                        receiveSocket.close();
//                    }
//                }
//            }
//        });
//        Handler handler = new Handler(Looper.getMainLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                Logger.getLogger("。。。SocketReceiveService。。。").log(Level.INFO, "socket监听服务正在运行。。。---------------" + new Date());
//                sendEmptyMessageDelayed(1, 500L);
//            }
//        };
//        handler.sendEmptyMessageDelayed(1, 500L);
//        return super.onStartCommand(intent, flags, startId);
//    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}