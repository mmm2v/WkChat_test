package com.weike.test.audio;

import com.weike.test.common.AudioConstants;

public class RecorderFactory {

	public static RecorderInterface.Recorder getRecorder(String type) {
//		switch (type) {
//			case AppConstants.FORMAT_WAV:
//				return WavRecorder.getInstance();
//			case AudioConstants.FORMAT_AAC:
//				return AacRecorder.getInstance();
//			default:
//				return null;
//		}
		return AacRecorder.getInstance();
	}
}
