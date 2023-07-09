package com.weike.test.audio;

import android.content.Context;

import com.weike.test.common.AudioConstants;

import java.io.File;

public class FileRepositoryImpl {
	private final static String TAG = FileRepositoryImpl.class.getSimpleName();
	private volatile static FileRepositoryImpl instance;

	private File recordDirectory;
	private PrefsImpl prefs;

	private FileRepositoryImpl(Context context, PrefsImpl prefs) {
		recordDirectory = FileUtil.getPrivateMusicStorageDir(context, AudioConstants.RECORDS_DIR);
		this.prefs = prefs;
	}

	public static FileRepositoryImpl getInstance(Context context, PrefsImpl prefs) {
		if (instance == null) {
			synchronized (FileRepositoryImpl.class) {
				if (instance == null) {
					instance = new FileRepositoryImpl(context, prefs);
				}
			}
		}
		return instance;
	}

	public String getRecordFileName(String format){
		return recordDirectory.getAbsolutePath() + File.separator + "Audio-1." + format;
	}
}
