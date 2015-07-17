package com.leo.android.audiovideoaudiomanager;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author lj
 * 1. AudioManager. getSystemService(Context¡£AUDIO_SERVICE)
 * 
 * 2. AudioManager request the audio focus, Sets the speakerphone on 
 * 2.1 requestAudioFocus(AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint)
 * 2.2 setSpeakerphoneOn(boolean false)
 * 
 * 3. SoundPool load sound resource 
 * 3.1 set a SoundPool.OnLoadCompletedListener on mSoundPool
 * 3.2 play audio. play (int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
 * 
 * 4. unload sound resources and release SoundPool within OnPause() method
 *    sets the speakerphone off.
 * 
 * there is a bug in this sample, when the phone screen light off, then light on again, mSoundPool released, 
 * so it will cast NullPointerException
 */
public class AudioManagerActivity extends Activity {
	private static final String TAG = "AudioManagerActivity";
	private TextView mVolumeView;
	private Button mVolumeUp, mVolumeDown, mAudioPlay;
	
	private int mVolumeNum = 6;
	private final int mMaxVolume = 10;
	private final int mMinVolume = 0;
	private AudioManager mAudioManager;
	private SoundPool mSoundPool;
	private boolean mCanAudioPlay;
	private int mSoundId;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_manager);
		
		//get references.
		mVolumeView = (TextView)findViewById(R.id.volume);
		mVolumeView.setText(String.valueOf(mVolumeNum));
		mVolumeDown = (Button)findViewById(R.id.volume_down);
		mVolumeUp = (Button)findViewById(R.id.volume_up);
		mAudioPlay = (Button)findViewById(R.id.play);
		mAudioPlay.setEnabled(false);;    /*disable the button before the sound is ready*/
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		//set listener on the button(control volume up)
		mVolumeUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//plays a sound effect
				mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
				if(mVolumeNum < mMaxVolume){
					mVolumeNum += 2;
					mVolumeView.setText(String.valueOf(mVolumeNum));
				}
			}
		});
		
		//set listener on the button(control volume down)
		mVolumeDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//plays a sound effect
				mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
				if(mVolumeNum > mMinVolume){
					mVolumeNum -= 2;
					mVolumeView.setText(String.valueOf(mVolumeNum));
				}
			}
		});
		
		//set listener on play button
		mAudioPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCanAudioPlay){
					/* 
					 * parameters£º 
					 * soundID	    a soundID returned by the load() function
					 * leftVolume	left volume value (range = 0.0 to 1.0)
					 * rightVolume 	right volume value (range = 0.0 to 1.0)
					 * priority	    stream priority (0 = lowest priority)
					 * loop	        loop mode (0 = no loop, -1 = loop forever)
					 * rate	        playback rate (1.0 = normal playback, range 0.5 to 2.0)
					 * 
					 * return: non-zero streamID if successful, zero if failed
					 * 
					 * public final int play (int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
					 */
					Log.i(TAG, "Play audio.");
					//parameters: the type of leftVolume and rightVolume is float(0~1.0),mVolumeNum/mMaxVolume must convert to float
					int result1 = mSoundPool.play(mSoundId, (float)mVolumeNum/mMaxVolume, (float)mVolumeNum/mMaxVolume, 1, 0, 1.0f); 
					Log.i(TAG, "Play audio, result: " + result1);
				}
				
			}
		});
		
		
		//load sound source. 
		//public int load (Context context, int resId, int priority)
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);  /*deprecated, should use SoundPool.Builder*/
		mSoundId = mSoundPool.load(this, R.raw.slow_whoop_bubble_pop, 1);
		
		//set a SoundPool.OnLoadCompletedListener on mSoundPool
		mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				// TODO Auto-generated method stub
				if(0 == status){   /*the status of load operation(0 == success)*/
					mAudioPlay.setEnabled(true); /*sound source is ready, enable the play button*/
					Log.i(TAG, "Success to load the sound.");
				}else{
					Log.i(TAG, "Failed to load the sound.");
					finish();    //exit app
				}
			}
		});
		
		//request audio focus
		int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		mCanAudioPlay = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;
		
	}
	
	//listen for audiofocus change
	AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener(){

		@Override
		public void onAudioFocusChange(int focusChange) {
			// TODO Auto-generated method stub
			if(AudioManager.AUDIOFOCUS_LOSS == focusChange){
				mAudioManager.abandonAudioFocus(afChangeListener);  /*AudioManage abandon audiofocus*/
				Log.i(TAG, "Failed focus audio.");
			}
		}
		
	};


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		mAudioManager.setSpeakerphoneOn(true);  /*Sets the speakerphone on*/
		mAudioManager.loadSoundEffects();    /*load sound effect*/
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(null != mSoundPool){
			mSoundPool.unload(mSoundId);   /*unload a sound from a sound id*/
			mSoundPool.release();         /*release sound pool*/
			mSoundPool = null;
		}
		mAudioManager.setSpeakerphoneOn(false);  /*Sets the speakerphone off.*/
		mAudioManager.unloadSoundEffects();   /*unload sound effect*/
		super.onPause(); 
	}
	
	
	
}
