package com.weike.test.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.weike.test.R;
import com.weike.test.audio.RecordVMFactory;
import com.weike.test.audio.RecordViewModel;
import com.weike.test.utils.RecorderUtils;

import java.io.File;

public class RecordTestActivity extends AppCompatActivity {

    private String TAG = "tag11";

    private boolean isRecording = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_test);

        // 设置录音文件保存路径
        File recordFile = new File(getExternalFilesDir(null), "recording.3gp");
        String filePath = recordFile.getAbsolutePath();
        Log.d(TAG, "filepath " + filePath);

        RecordViewModel recordViewModel = new RecordVMFactory().create(RecordViewModel.class);
        recordViewModel.getRecording();
        recordViewModel.getRecordingDuration().observe(this, duration ->{
            Log.d(TAG, "duration:" +duration);
        });
        Button btn1 = findViewById(R.id.btn_test_record);
//        btn1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // 手指按下时开始录音
//                        RecorderUtils.startRecording(filePath);
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        // 手指抬起时停止录音
//                        RecorderUtils.stopRecording();
//                        return true;
//                }
//                return false;
//            }
//        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordViewModel.record();
                if (isRecording){
                    recordViewModel.stopRecord();
                    isRecording = false;
                    btn1.setText("录音");
                } else {
                    recordViewModel.record();
                    isRecording = true;
                    btn1.setText("停止录音");
                }
            }
        });

        Button btn2 = findViewById(R.id.btn_test_play);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecorderUtils.startPlayback(filePath);
            }
        });
    }
}