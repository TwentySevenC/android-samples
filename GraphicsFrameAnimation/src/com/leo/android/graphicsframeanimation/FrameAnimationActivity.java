package com.leo.android.graphicsframeanimation;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.widget.ImageView;

public class FrameAnimationActivity extends Activity {
	private ImageView mImageView;
	private AnimationDrawable mAnimationDrawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame_animation);
		mImageView = (ImageView) findViewById(R.id.frame_image);
//		AnimationDrawable drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.view_animation);
		mImageView.setBackgroundResource(R.drawable.view_animation);
		mAnimationDrawable = (AnimationDrawable) mImageView.getBackground();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mAnimationDrawable.isRunning()){
			mAnimationDrawable.stop();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			mAnimationDrawable.start();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.frame_animation, menu);
		return true;
	}

}
