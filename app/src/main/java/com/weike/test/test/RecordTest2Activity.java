package com.weike.test.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.weike.test.R;
import com.weike.test.audio.AudioRecorderPlayer;
import com.weike.test.audio.AudioTrackManager;
import com.weike.test.utils.AudioTransferManager;

public class RecordTest2Activity extends AppCompatActivity{

    private String TAG = "tag11";

    AudioTrackManager audioTrackManager = AudioTrackManager.getInstance();
    private boolean isPlaying = false;
    private boolean isRecording = false;

    private AudioRecorderPlayer player;

    private AudioTransferManager transferManager = new AudioTransferManager();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = new AudioRecorderPlayer();
        setContentView(R.layout.activity_record_test2);
        transferManager.startServer();

        Button btnRecord = findViewById(R.id.btn_record);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecording){
                    transferManager.startAudioTransfer();
                    isRecording = true;
                }
            }
        });
        Button btnRecordStop = findViewById(R.id.btn_record_stop);
        btnRecordStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording){
                    transferManager.stopAudioTransfer();
                    isRecording = false;
                }
            }
        });
        Button btnRecordPlay = findViewById(R.id.btn_record_play);
        btnRecordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    btnRecordPlay.setText("播放");
                    audioTrackManager.stopPlay();
                    isPlaying = false;
                } else {
                    btnRecordPlay.setText("停止");
                    audioTrackManager.startPlay(R.raw.ring1);
                    isPlaying = true;
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transferManager.stopServer();
    }
}