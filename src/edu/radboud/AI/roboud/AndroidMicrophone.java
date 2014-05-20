package edu.radboud.AI.roboud;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Gebruiker on 20-5-14.
 * Based on: http://stackoverflow.com/questions/8499042/android-audiorecord-example
 */
public class AndroidMicrophone {

    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private int BufferElements2Rec;
    private int BytesPerElement;

    private AudioRecord audioRecord;

    private RoboudController controller;

    public AndroidMicrophone(RoboudController _controller) {
        this.controller = _controller;

        BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
        BytesPerElement = 2; // 2 bytes in 16bit format
    }

    public void startRecording() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);
        audioRecord.startRecording();
        audioRecord.setPositionNotificationPeriod((int) (RECORDER_SAMPLERATE * 0.1)); // Every 0.1 second
        audioRecord.setRecordPositionUpdateListener(controller);
    }

    public void stopRecording() {
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    public AudioRecord getAudioRecord() {
        return audioRecord;
    }
}
