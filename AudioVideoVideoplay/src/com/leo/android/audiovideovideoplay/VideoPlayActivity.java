package com.leo.android.audiovideovideoplay;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayActivity extends Activity {
	private VideoView mVideoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_play);
		
		//get the reference for the VideoView 
		mVideoView = (VideoView)findViewById(R.id.video_view);
		
		//create a MediaController 
		final MediaController mController = new MediaController(getApplicationContext(), true);
		//disable the MediaCOntroller before MediaPlayer prepared
		mController.setEnabled(false);
		
		mVideoView.setMediaController(mController);
		
		//set the video uri for VideoView
		mVideoView.setVideoURI(Uri.parse("android.resource://com.leo.android.audiovideovideoplay/raw/moon"));
		                                                   //com.leo.android.audiovideovideoplay
		//add OnPreparedListener to enable mController once the mediaPlay prepared
		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mController.setEnabled(true);
			}
		});
	}
	
	//clean up and release resource
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(mVideoView != null && mVideoView.isPlaying()){
			mVideoView.stopPlayback();
			mVideoView = null;    //keep in mind
		}
		super.onPause();
	}
	
	
}
