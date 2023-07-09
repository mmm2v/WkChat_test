package com.weike.test.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.weike.test.R;
import com.weike.test.utils.NetworkUtils;
import com.weike.test.utils.PermissionRequestUtil;

@RequiresApi(api = Build.VERSION_CODES.S)
public class TestActivity extends AppCompatActivity {

    private String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Context context = this;
        String[] PERMISSIONS = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION

        };

        PermissionRequestUtil.requestPermissions(this, PERMISSIONS);
        Button btn1 = findViewById(R.id.btn_test1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SensorTestActivity.class);
                startActivity(intent);
            }
        });

        Button btn2 = findViewById(R.id.btn_test2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VoiceModelActivity.class);
                startActivity(intent);
            }
        });

        Button btn8 = findViewById(R.id.btn_test8);

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BluetoothTestActivity.class);
                startActivity(intent);
            }
        });

        Button btn9 = findViewById(R.id.btn_test9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecordTestActivity.class);
                startActivity(intent);
            }
        });

        Button btn10 = findViewById(R.id.btn_test10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = NetworkUtils.getIPAddress();
                Toast.makeText(context, "ip:" + ip, Toast.LENGTH_SHORT).show();
            }
        });

//        Button btn3 = findViewById(R.id.btn_test3);
//        String start = "启动按键监听服务";
//        String stop = "终止按键监听服务";
//        SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
//        boolean isRunning = sharedPreferences.getBoolean("isRunning", false);
//        Log.d(TAG, "----------测试-----------:" + isRunning);
//        final boolean[] isServiceStart = {isRunning};
//        if (isServiceStart[0]) {
//            btn3.setText(stop);
//        } else {
//            btn3.setText(start);
//        }
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isServiceStart[0]) {
//                    btn3.setText(start);
//                    Toast.makeText(context, stop, Toast.LENGTH_SHORT).show();
//                    Intent service = new Intent(context, SendLocationService.class);
//                    context.stopService(service);
//                    isServiceStart[0] = false;
//                    sharedPreferences.edit().putBoolean("isRunning", false).apply();
//                } else {
//                    btn3.setText(stop);
//                    Toast.makeText(context, start, Toast.LENGTH_SHORT).show();
//                    Intent service = new Intent(context, SendLocationService.class);
//                    context.startForegroundService(service);
//                    isServiceStart[0] = true;
//                    sharedPreferences.edit().putBoolean("isRunning", true).apply();
//                }
//            }
//        });

        Button btn4 = findViewById(R.id.btn_test4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KeyEventTestActivity.class);
                startActivity(intent);
            }
        });
        Button btn5 = findViewById(R.id.btn_test5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecordTest2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}