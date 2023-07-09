package com.weike.test.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.weike.test.R;

public class VoiceModelActivity extends AppCompatActivity {

    private String TAG = "tag13";
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean isUsingSpeaker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_model);
        mediaPlayer = MediaPlayer.create(this, R.raw.test); // 指定音频文件
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Button playButton = findViewById(R.id.btn_test_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        Button toggleButton = findViewById(R.id.btn_test_change);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAudioOutput();
            }
        });
    }

    private void playAudio() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // 开始播放音频
        }
    }

    private void toggleAudioOutput() {
        if (isUsingSpeaker) {
            // 切换到听筒
            audioManager.setSpeakerphoneOn(false);
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            isUsingSpeaker = false;
        } else {
            // 切换到扬声器
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.setSpeakerphoneOn(true);
            isUsingSpeaker = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop(); // 停止播放音频
        }
        mediaPlayer.release(); // 释放MediaPlayer资源
    }
}