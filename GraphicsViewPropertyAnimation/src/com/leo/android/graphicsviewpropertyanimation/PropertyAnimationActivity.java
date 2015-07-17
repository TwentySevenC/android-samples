package com.leo.android.graphicsviewpropertyanimation;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

public class PropertyAnimationActivity extends Activity {
	private ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_animation);
		
	}
	
	Runnable fadIn = new Runnable() {
		
		@Override
		public void run() {
			//View's animate() method returns a ViewPropertyAnimator object, 
			//which can be used to animate specific properties on this View.
			mImageView.animate()
			.alphaBy(1)
			.setDuration(3000)
			.setInterpolator(new LinearInterpolator())
			.withEndAction(rotate);
			
		}
	};
	
	Runnable rotate = new Runnable() {
		
		@Override
		public void run() {
			mImageView.animate().rotationBy(720)
			.setDuration(4000)
			.setInterpolator(new AccelerateInterpolator())
			.withEndAction(translate);
		}
	};
	
	Runnable translate = new Runnable() {
		
		@Override
		public void run() {
			float translation = getResources().getDimension(R.dimen.translation);
			mImageView.animate().setDuration(3000)
			.translationXBy(translation).translationYBy(translation)
			.setInterpolator(new AccelerateDecelerateInterpolator())
			.withEndAction(scale);
			
		}
	};
	
	Runnable scale = new Runnable() {
		
		@Override
		public void run() {
			mImageView.animate().setDuration(3000)
			.scaleXBy(1).scaleYBy(1).setInterpolator(new AnticipateInterpolator())
			.withEndAction(fadout);
		}
	};
	
	Runnable fadout = new Runnable() {
		
		@Override
		public void run() {
			mImageView.animate().setDuration(3000)
			.alpha(0).setInterpolator(new OvershootInterpolator());
			
		}
	};
	
	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		mImageView = (ImageView)findViewById(R.id.bubble_view);
		if(hasFocus){
			fadIn.run();
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.property_animation, menu);
		return true;
	}

}
