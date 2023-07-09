package com.weike.test.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.weike.test.R;

public class KeyEventTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_event_test);


//        // 开启无障碍
//        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
//
//        Button btn4 = findViewById(R.id.btn_test_key);
//        String start420 = "启动按键监听服务";
//        String stop420 = "终止按键监听服务";
//        SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
//        boolean isRunning420 = sharedPreferences.getBoolean("isRunning420", false);
//        final boolean[] isServiceStart420 = {isRunning420};
//        if (isServiceStart420[0]) {
//            btn4.setText(stop420);
//        } else {
//            btn4.setText(start420);
//        }
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isServiceStart420[0]) {
//                    btn4.setText(start420);
//                    Toast.makeText(btn4.getContext(), stop420, Toast.LENGTH_SHORT).show();
//                    Intent service = new Intent(btn4.getContext(), SendLocationService3.class);
//                    stopService(service);
//                    isServiceStart420[0] = false;
//                    sharedPreferences.edit().putBoolean("isRunning", false).apply();
//                } else {
//                    btn4.setText(stop420);
//                    Toast.makeText(btn4.getContext(), start420, Toast.LENGTH_SHORT).show();
//                    Intent service = new Intent(btn4.getContext(), SendLocationService3.class);
//                    startForegroundService(service);
//                    isServiceStart420[0] = true;
//                    sharedPreferences.edit().putBoolean("isRunning", true).apply();
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(this, "按下," + "keyCode:" + keyCode, Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Toast.makeText(this, "松开," + "keyCode:" + keyCode, Toast.LENGTH_SHORT).show();
        return super.onKeyUp(keyCode, event);
    }
}