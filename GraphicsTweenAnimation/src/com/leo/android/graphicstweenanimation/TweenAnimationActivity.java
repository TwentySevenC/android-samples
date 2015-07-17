package com.leo.android.graphicstweenanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 
 * @author lj
 * View Animation: Tween Animation and Frame Animation
 * Tween Animation: Creates an animation by performing a series of transformations on a single image with an Animation
 * size, transparency, orientation, position
 * TranslationDrawable, AnimationDrawable, Animation
 */
public class TweenAnimationActivity extends Activity {
	private ImageView mImageView;
	private Animation mAnim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tween_animation);
		mImageView = (ImageView)findViewById(R.id.tween_image);
		
//		mAnim = (Animation) getResources().getAnimation(R.anim.tween_animation); /*this is wrong*/
		
		mAnim = AnimationUtils.loadAnimation(this, R.anim.tween_animation);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			mImageView.startAnimation(mAnim);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tween_animation, menu);
		return true;
	}

}
