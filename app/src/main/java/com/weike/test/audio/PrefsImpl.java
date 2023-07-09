package com.weike.test.audio;

import android.content.Context;
import android.content.SharedPreferences;

import com.weike.test.common.AudioConstants;

public class PrefsImpl {

	private static final String PREF_NAME = "record_pref";

	private static final String PREF_KEY_SETTING_RECORDING_FORMAT = "setting_recording_format";
	private static final String PREF_KEY_SETTING_BITRATE = "setting_bitrate";
	private static final String PREF_KEY_SETTING_SAMPLE_RATE = "setting_sample_rate";
	private static final String PREF_KEY_SETTING_CHANNEL_COUNT = "setting_channel_count";

	private SharedPreferences sharedPreferences;

	private volatile static PrefsImpl instance;

	private PrefsImpl(Context context) {
		sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	public static PrefsImpl getInstance(Context context) {
		if (instance == null) {
			synchronized (PrefsImpl.class) {
				if (instance == null) {
					instance = new PrefsImpl(context);
				}
			}
		}
		return instance;
	}

	public void setSettingFormat(String formatKey) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(PREF_KEY_SETTING_RECORDING_FORMAT, formatKey);
		editor.apply();
	}

	public String getSettingFormat() {
		return sharedPreferences.getString(PREF_KEY_SETTING_RECORDING_FORMAT, AudioConstants.DEFAULT_RECORDING_FORMAT);
	}

	public void setSettingSampleRate(int sampleRate) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(PREF_KEY_SETTING_SAMPLE_RATE, sampleRate);
		editor.apply();
	}

	public int getSettingSampleRate() {
		return sharedPreferences.getInt(PREF_KEY_SETTING_SAMPLE_RATE, AudioConstants.RECORD_SAMPLE_RATE_8000);
	}

	public void setSettingBitrate(int rate) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(PREF_KEY_SETTING_BITRATE, rate);
		editor.apply();
	}

	public int getSettingBitrate() {
		return sharedPreferences.getInt(PREF_KEY_SETTING_BITRATE, AudioConstants.DEFAULT_RECORD_ENCODING_BITRATE);
	}

	public void setSettingChannelCount(int count) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(PREF_KEY_SETTING_CHANNEL_COUNT, count);
		editor.apply();
	}

	public int getSettingChannelCount() {
		return sharedPreferences.getInt(PREF_KEY_SETTING_CHANNEL_COUNT, AudioConstants.DEFAULT_CHANNEL_COUNT);
	}
}
