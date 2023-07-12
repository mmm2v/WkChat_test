package com.weike.test.utils;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class AudioTransferManager {
    private static final String TAG = "AudioTransferManager";
    private static final int mPort = 7777;
    private static final String mInetAddress = "192.168.1.3";

    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord mAudioRecord;
    private Thread mRecordThread;
    private boolean isRecording = false;

    private DatagramSocket sendSocket;

    private DatagramSocket receiveSocket;

    public void startAudioTransfer() {
        startRecording();
    }

    public void stopAudioTransfer() {
        stopRecording();
    }

    @SuppressLint("MissingPermission")
    private void startRecording() {
        if (isRecording) {
            return;
        }
        isRecording = true;

        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG,
                AUDIO_FORMAT, bufferSize);

        mAudioRecord.startRecording();

        mRecordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[bufferSize];
                try {
                    while (isRecording) {
                        int readSize = mAudioRecord.read(buffer, 0, bufferSize);
                        if (readSize > 0) {
                            // 将音频数据发送给对端
                            sendData(buffer, readSize);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mRecordThread.start();
    }

    private void stopRecording() {
        isRecording = false;

        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }

        if (mRecordThread != null) {
            try {
                mRecordThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mRecordThread = null;
        }
    }

    private void sendData(byte[] data, int size) throws IOException {
        Log.d(TAG, "sendData: -----data:" + Arrays.toString(data));
        Log.d(TAG, "sendData: -----size:" + size);
        try {
            if (sendSocket == null) {
                sendSocket = new DatagramSocket();
            }
            DatagramPacket packet = new DatagramPacket(data, size, InetAddress.getByName(mInetAddress), mPort);
            sendSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 接收并播放音频数据
                receiveAndPlayData();
            }
        });

        serverThread.start();
    }

    public void stopServer() {
        if (receiveSocket != null) {
            receiveSocket.close();
            receiveSocket = null;
        }
        if (sendSocket != null) {
            sendSocket.close();
            sendSocket = null;
        }
    }

    private void receiveAndPlayData() {
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AUDIO_FORMAT);
        AudioTrack audioTrack = new AudioTrack.Builder()
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AUDIO_FORMAT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build())
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build();

        audioTrack.play();

        try {
            while (true) {
                if (receiveSocket == null) {
                    receiveSocket = new DatagramSocket(mPort);
                }
                byte[] bytes = new byte[bufferSize];
                DatagramPacket receivePacket = new DatagramPacket(bytes, 0, bytes.length);
                receiveSocket.receive(receivePacket);
                Log.d(TAG, "receiveMsg: " + new String(receivePacket.getData()));
                if (receivePacket.getLength() > 0) {
                    // 播放音频数据
                    audioTrack.write(bytes, 0, bytes.length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            audioTrack.stop();
            audioTrack.release();
        }
    }
}
