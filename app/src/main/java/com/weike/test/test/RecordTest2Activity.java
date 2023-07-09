package com.weike.test.test;

import android.annotation.SuppressLint;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.weike.test.R;
import com.weike.test.audio.AudioRecorderPlayer;
import com.weike.test.audio.AudioTrackManager;
import com.weike.test.utils.ThreadUtils;

public class RecordTest2Activity extends AppCompatActivity{

    private String TAG = "tag11";

    AudioTrackManager audioTrackManager = AudioTrackManager.getInstance();
    private boolean isPlaying = false;
    private boolean isRecording = false;

    private AudioRecorderPlayer player;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = new AudioRecorderPlayer();
        setContentView(R.layout.activity_record_test2);

        Button btnRecord = findViewById(R.id.btn_record);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!isRecording){
//                    audioTrackManager.startRecording();
//                    isRecording = true;
//                }
                ThreadUtils.getInstance().executeTask(new Runnable() {
                    @Override
                    public void run() {
                        player.startRecordingAndPlaying();
                    }
                });
            }
        });
        Button btnRecordStop = findViewById(R.id.btn_record_stop);
        btnRecordStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording){
                    audioTrackManager.stopRecording();
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
}