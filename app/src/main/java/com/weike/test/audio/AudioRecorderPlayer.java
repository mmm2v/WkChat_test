package com.weike.test.audio;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.NoiseSuppressor;
import android.util.Log;

public class AudioRecorderPlayer {
    private static final String TAG = "AudioRecorderPlayer";
    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private boolean isRecording = false;

    @SuppressLint("MissingPermission")
    public AudioRecorderPlayer(){
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);
        audioTrack = new AudioTrack(android.media.AudioManager.STREAM_MUSIC, SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AUDIO_FORMAT, BUFFER_SIZE, AudioTrack.MODE_STREAM);
        if (NoiseSuppressor.isAvailable()) {
            NoiseSuppressor noiseSuppressor = NoiseSuppressor.create(audioRecord.getAudioSessionId());
            if (noiseSuppressor != null) {
                noiseSuppressor.setEnabled(true);
            }
        }

        if (AcousticEchoCanceler.isAvailable()) {
            AcousticEchoCanceler echoCanceler = AcousticEchoCanceler.create(audioRecord.getAudioSessionId());
            if (echoCanceler != null) {
                echoCanceler.setEnabled(true);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void startRecordingAndPlaying() {

        isRecording = true;
        audioRecord.startRecording();
        audioTrack.play();

        byte[] buffer = new byte[BUFFER_SIZE];
        while (isRecording) {
            int bytesRead = audioRecord.read(buffer, 0, BUFFER_SIZE);
            audioTrack.write(buffer, 0, bytesRead);
        }

        stopRecordingAndPlaying();
    }

    public void stopRecordingAndPlaying() {
        if (isRecording) {
            isRecording = false;

            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
                audioRecord = null;
            }

            if (audioTrack != null) {
                audioTrack.stop();
                audioTrack.release();
                audioTrack = null;
            }
        }
    }
}
