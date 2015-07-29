package com.leo.android.audiovideo.audiorecording;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioRecordingActivity extends Activity {
	private static final String TAG = "AudioRecordingActivity";
	private Button mBtnRecord = null;
	private Button mBtnPlay = null;
	private MediaRecorder mMediaRecorder = null;
	private MediaPlayer mMediaPlayer = null;
	
	private AudioManager mAudioManager = null;
	private boolean mIsPlay = false ,mIsRecord = false;
	private boolean mCanPlayAudio = false;

	private static final String mFileName = 
			Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
	 
	/*public AudioRecordingActivity() {
		// TODO Auto-generated constructor stub
		mFileName = ;
		mFileName += "/audiorecordtest.3gp";
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_recording);
		
		File file = new File(mFileName);    //new a file
		
		mBtnPlay = (Button)findViewById(R.id.play);
		mBtnRecord = (Button)findViewById(R.id.record);
		
		mAudioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		int result = mAudioManager.requestAudioFocus(ofChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		mCanPlayAudio = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
		
		
		mBtnRecord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mIsRecord = !mIsRecord;
				setButtonEnable(mBtnPlay, mIsRecord);
				onRecord(mIsRecord);
			}
		});
		
		mBtnPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mIsPlay = !mIsPlay;
				setButtonEnable(mBtnRecord, mIsPlay);
				onPlay(mIsPlay);
			}
		});
	}
	
	private void setButtonEnable(Button button, boolean enable){
		if(enable){
			button.setEnabled(false);
		}else{
			button.setEnabled(true);
		}
	}
	
	//play audio
	private void playAudio(){
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(mFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "Unable to set mediaplayer's data source.");
			e.printStackTrace();
		} 
		
		try {
			mMediaPlayer.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "Failed to prepar mediaplayer.");
			e.printStackTrace();
		} 
		
		mMediaPlayer.start();
	}
	
	//stop play audio, release mediaPlayer and set to null
	private void stopAudio(){
		if(mMediaPlayer != null){
			if(mMediaPlayer.isPlaying()) mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	//refer to playState, play or stop audio
	private void onPlay(boolean playState){
		if(playState && mCanPlayAudio){
			playAudio();                            /*Only when audio in play state && audioManager get the focus, can play audio*/
		}else{
			stopAudio();
		}
	}
	
	/**
	 * 1. Create a new instance of android.media.MediaRecorder.
	 * 2. Set the audio source using MediaRecorder.setAudioSource(). You will probably want to use MediaRecorder.AudioSource.MIC.
	 * 3. Set output file format using MediaRecorder.setOutputFormat().
	 * 4. Set output file name using MediaRecorder.setOutputFile().
	 * 5. Set the audio encoder using MediaRecorder.setAudioEncoder().
	 * 6. Call MediaRecorder.prepare() on the MediaRecorder instance.
	 * 7. To start audio capture, call MediaRecorder.start().
	 * 8¡£ To stop audio capture, call MediaRecorder.stop().
	 * 9. When you are done with the MediaRecorder instance, call MediaRecorder.release() on it. 
	 *    Calling MediaRecorder.release() is always recommended to free the resource immediately.
	 */
	private void startRecord(){
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mMediaRecorder.setOutputFile(mFileName);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mMediaRecorder.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "Failed to prepar MediaRecorder");
			e.printStackTrace();
		} 
		
		mMediaRecorder.start();
	}
	
	private void stopRecord(){
		if(mMediaRecorder != null){
//			mMediaRecorder.reset();     /*make MediaRecorder initial*/
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}
	
	//start or stop record refer to the recordState
	private void onRecord(boolean recordState){
		if(recordState){
			startRecord();
		}else{
			stopRecord();
		}
	}
	
	AudioManager.OnAudioFocusChangeListener ofChangeListener = new OnAudioFocusChangeListener() {
		
		@Override
		public void onAudioFocusChange(int focusChange) {
			// TODO Auto-generated method stub
			switch (focusChange) {
			case AudioManager.AUDIOFOCUS_GAIN:
				if(mMediaPlayer != null){
					mMediaPlayer.setVolume(1.0f, 1.0F);
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
				//stop playback if necessary
				if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
					stopAudio();
				}
				if(mAudioManager != null){
					mAudioManager.abandonAudioFocus(ofChangeListener);
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				// Lost focus for a short time, but we have to stop
	            // playback. We don't release the media player because playback
	            // is likely to resume
				if(mMediaPlayer != null){
					if(mMediaPlayer.isPlaying()) mMediaPlayer.pause();
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				// Lost focus for a short time, but it's ok to keep playing
	            // at an attenuated level
				if(mMediaPlayer != null){
					if(mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
				}
				break;
			default:
				break;
			}
		}
	};

	
	//release resources
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(mMediaPlayer != null){
			stopAudio();
		}
		if(mMediaRecorder != null){
			stopRecord();
		}
		super.onPause();
	}
	
	
	
}
