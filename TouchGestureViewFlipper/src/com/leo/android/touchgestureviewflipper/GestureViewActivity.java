package com.leo.android.touchgestureviewflipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class GestureViewActivity extends Activity {

	private ViewFlipper mViewFlipper;
	private TextView mTextView1;
	private TextView mTextView2;
	private GestureDetector mGestureDetector;
	private Animation mInAnimation;
	private Animation mOutAnimation;
	private int mCurrentLayoutState = 0, mCount = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_view);
		mViewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
		mTextView1 = (TextView)findViewById(R.id.text_view1);
		mTextView2 = (TextView)findViewById(R.id.text_view2);
		mInAnimation = AnimationUtils.loadAnimation(this, R.anim.in_frome_right);
		mOutAnimation = AnimationUtils.loadAnimation(this, R.anim.out_from_left);
		
		mViewFlipper.setInAnimation(mInAnimation);
		mViewFlipper.setOutAnimation(mOutAnimation);
		mTextView1.setText(String.valueOf(mCount));
		
		mGestureDetector = new GestureDetector (this, new GestureDetector.SimpleOnGestureListener(){

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				if(velocityX < -5.0f){
					mCurrentLayoutState = mCurrentLayoutState == 0 ? 1 : 0;
					startViewFlipper(mCurrentLayoutState);
				}
				return true;
			}
			
		});
	}
	
	private void startViewFlipper(int currentLayoutState){
		
		mCount++;
		if(mCurrentLayoutState == 0){
			mTextView1.setText(String.valueOf(mCount));
		}else{
			mTextView2.setText(String.valueOf(mCount));
		}
		mViewFlipper.showPrevious();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gesture_view, menu);
		return true;
	}

}
