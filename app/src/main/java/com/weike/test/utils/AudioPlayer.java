package com.weike.test.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioPlayer {
    private static AudioPlayer instance;
    private final MediaPlayer mediaPlayer;
    private final AudioManager audioManager;
    private boolean isPaused;

    private AudioPlayer(Context context) {
        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static AudioPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new AudioPlayer(context.getApplicationContext());
        }
        return instance;
    }

    public void start(String filePath) {
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPaused = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        }
    }

    public void resume() {
        if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
        }
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        isPaused = false;
    }

    public void switchToEarpiece() {
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(false);
    }

    public void switchToSpeaker() {
        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(true);
    }
}

