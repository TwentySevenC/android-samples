package com.leo.android.grapicsvalueanimator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ValueAnimatorActivity extends Activity {
	private static final int RED = Color.RED;
	private static final int BLUE = Color.BLUE;
	private Button mButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_value_animator);
		
		mButton = (Button)findViewById(R.id.start_animator);
		
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				startValueAnimator();
			}
		});
	}

	public void startValueAnimator(){
		final ImageView imageView  = (ImageView)findViewById(R.id.value_animator_image);
		
		ValueAnimator animtor = ValueAnimator.ofObject(new ArgbEvaluator(), RED, BLUE);
		animtor.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// TODO Auto-generated method stub
				imageView.setBackgroundColor((Integer)animator.getAnimatedValue());
			}
		});
		
		animtor.setDuration(3000);
		animtor.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.value_animator, menu);
		return true;
	}

}
