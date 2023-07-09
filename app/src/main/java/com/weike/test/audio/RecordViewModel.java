package com.weike.test.audio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RecordViewModel extends ViewModel {

	private RecorderManager recorderManager;
	private DateRecordState dateRecordState;

	public void init() {
		dateRecordState = DateRecordState.getInstance();
		recorderManager = RecorderManager.getInstance();
	}

	public LiveData<Integer> getRecording() {
		return dateRecordState.getRecordingState();
	}

	public LiveData<Long> getRecordingDuration() {
		return dateRecordState.getRecordingDuration();
	}

	public void record() {
		recorderManager.startRecording();
	}

	public void stopRecord() {
		recorderManager.stopRecording();
	}

}