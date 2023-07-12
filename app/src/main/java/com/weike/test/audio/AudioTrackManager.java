package com.weike.test.audio;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import com.weike.test.utils.MyApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;

public class AudioTrackManager {
    private final String TAG = "-----AudioTrack----";
    private AudioTrack mAudioTrack;
    private DataInputStream mDis;//播放文件的数据流
    private Thread mRecordThread;
    private boolean isStart = false;
    private volatile static AudioTrackManager mInstance;

    //指定采样率
    private static final int mSampleRateInHz = 8000;
    //指定捕获音频的声道数目
    private static final int mChannelConfig = AudioFormat.CHANNEL_OUT_MONO;
    //指定音频量化位数
    private static final int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private final int bufferSizeInBytes = 160;

    private AudioRecord mAudioRecord;
    private DataOutputStream mDataOutputStream;
    private Thread mPlayThread;
    private boolean isRecording = false;

    private static final int PORT = 12345;

    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private ServerSocket mServerSocket;
    private Socket mClientSocket;
    private DataInputStream mInputStream;
    private DataOutputStream mOutputStream;

    public AudioTrackManager() {
        initData();
    }

    private void initData() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFormat audioFormat = new AudioFormat.Builder()
                .setEncoding(mAudioFormat)
                .setSampleRate(mSampleRateInHz)
                .setChannelMask(mChannelConfig)
                .build();

        Log.d(TAG, "----bufferSizeInBytes----: " + bufferSizeInBytes);
        mAudioTrack = new AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(bufferSizeInBytes)
                .build();
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static AudioTrackManager getInstance() {
        if (mInstance == null) {
            synchronized (AudioTrackManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioTrackManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 销毁线程方法
     */
    private void destroyThread() {
        try {
            isStart = false;
            if (null != mRecordThread && Thread.State.RUNNABLE == mRecordThread.getState()) {
                try {
                    Thread.sleep(500);
                    mRecordThread.interrupt();
                } catch (Exception e) {
                    mRecordThread = null;
                }
            }
            mRecordThread = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mRecordThread = null;
        }
    }

    /**
     * 启动播放线程
     */
    private void startThread() {
        destroyThread();
        isStart = true;
        if (mRecordThread == null) {
            mRecordThread = new Thread(recordRunnable);
            mRecordThread.start();
        }
    }

    /**
     * 播放线程
     */
    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                //设置线程的优先级
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
                byte[] tempBuffer = new byte[bufferSizeInBytes];
                int readCount = 0;
                while (mDis.available() > 0) {
                    readCount = mDis.read(tempBuffer);
                    if (readCount == AudioTrack.ERROR_INVALID_OPERATION || readCount == AudioTrack.ERROR_BAD_VALUE) {
                        continue;
                    }
                    if (readCount != 0 && readCount != -1) {//一边播放一边写入语音数据
                        //判断AudioTrack未初始化，停止播放的时候释放了，状态就为STATE_UNINITIALIZED
                        if (mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED) {
                            initData();
                        }
                        mAudioTrack.play();
                        mAudioTrack.write(tempBuffer, 0, readCount);
                    }
                }
                stopPlay();//播放完就停止播放
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    /**
     * 播放文件
     *
     * @throws Exception
     */
    private void setPath(int file) throws Exception {
        mDis = new DataInputStream(MyApplication.getAppContext().getResources().openRawResource(file));
    }

    /**
     * 启动播放
     *
     * @param file
     */
    public void startPlay(int file) {
        try {
            Log.d(TAG, "---path----" + file);
            //AudioTrack未初始化
            if (mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED) {
                throw new RuntimeException("The AudioTrack is not uninitialized");
            } else {
                setPath(file);
                startThread();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        try {
            destroyThread();//销毁线程
            if (mAudioTrack != null) {
                if (mAudioTrack.getState() == AudioRecord.STATE_INITIALIZED) {//初始化成功
                    mAudioTrack.stop();//停止播放
                }
                if (mAudioTrack != null) {
                    mAudioTrack.release();//释放audioTrack资源
                }
            }
            if (mDis != null) {
                mDis.close();//关闭数据输入流
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public void startRecording() {
        Log.d(TAG, "--------startRecording: ");
        if (isRecording) {
            return;
        }
        isRecording = true;
        try {
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, mSampleRateInHz, AudioFormat.CHANNEL_IN_MONO,
                    mAudioFormat, bufferSizeInBytes);
            Log.d(TAG, "---------mAudioRecord: " + mAudioRecord.getBufferSizeInFrames());
            mDataOutputStream = new DataOutputStream(Files.newOutputStream(new File("record.txt").toPath()));
            mAudioRecord.startRecording();
            startRecordThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        isRecording = false;
        try {
            if (mAudioRecord != null) {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
            if (mDataOutputStream != null) {
                mDataOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startRecordThread() {
        Log.d(TAG, "--------startRecordThread: ");
        mPlayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[bufferSizeInBytes];
                while (isRecording) {
                    int readSize = mAudioRecord.read(buffer, 0, bufferSizeInBytes);
                    if (readSize > 0) {
                        try {
                            Log.d(TAG, "start: ----" + Arrays.toString(buffer));
                            mDataOutputStream.write(buffer, 0, readSize);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        mPlayThread.start();
    }

}